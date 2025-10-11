package com.naveen.aichat.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<WebSocketSession, CompletableFuture<WebSocketSession>> backendSessions = new ConcurrentHashMap<>();
    private final StandardWebSocketClient backendClient = new StandardWebSocketClient();

    @Override
    public void afterConnectionEstablished(WebSocketSession clientSession) throws Exception {
        CompletableFuture<WebSocketSession> backendFuture = new CompletableFuture<>();
        backendSessions.put(clientSession, backendFuture);
        backendClient.doHandshake(new WebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession backendSession) throws Exception {
                backendFuture.complete(backendSession);
            }

            @Override
            public void handleMessage(WebSocketSession backendSession, org.springframework.web.socket.WebSocketMessage<?> message) throws Exception {
                // Forward backend message to client
                if (clientSession.isOpen()) {
                    clientSession.sendMessage(message);
                }
            }

            @Override
            public void handleTransportError(WebSocketSession backendSession, Throwable exception) throws Exception {
                if (clientSession.isOpen()) clientSession.close(CloseStatus.SERVER_ERROR);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession backendSession, CloseStatus closeStatus) throws Exception {
                if (clientSession.isOpen()) clientSession.close(closeStatus);
                backendSessions.remove(clientSession);
            }

            @Override
            public boolean supportsPartialMessages() {
                return false;
            }
        }, "ws://localhost:8765").addCallback(
            result -> {},
            ex -> {
                if (clientSession.isOpen()) {
                    try { clientSession.sendMessage(new TextMessage("Failed to connect to backend WebSocket.")); clientSession.close(CloseStatus.SERVER_ERROR); } catch (Exception ignore) {}
                }
                backendSessions.remove(clientSession);
            }
        );
    }

    @Override
    protected void handleTextMessage(WebSocketSession clientSession, TextMessage message) throws Exception {
        CompletableFuture<WebSocketSession> backendFuture = backendSessions.get(clientSession);
        if (backendFuture == null) {
            clientSession.sendMessage(new TextMessage("Backend connection not available."));
            return;
        }
        backendFuture.whenComplete((backendSession, ex) -> {
            try {
                if (ex != null || backendSession == null || !backendSession.isOpen()) {
                    clientSession.sendMessage(new TextMessage("Backend connection not available."));
                } else {
                    backendSession.sendMessage(message);
                }
            } catch (Exception e) {
                try { clientSession.sendMessage(new TextMessage("Error forwarding message to backend.")); } catch (Exception ignore) {}
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession clientSession, CloseStatus status) throws Exception {
        CompletableFuture<WebSocketSession> backendFuture = backendSessions.remove(clientSession);
        if (backendFuture != null && backendFuture.isDone()) {
            WebSocketSession backendSession = backendFuture.getNow(null);
            if (backendSession != null && backendSession.isOpen()) {
                backendSession.close(status);
            }
        }
    }
}
