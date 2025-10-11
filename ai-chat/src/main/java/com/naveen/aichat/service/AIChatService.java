package com.naveen.aichat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIChatService {
/*
    @Autowired
    private AzureOpenAiChatClient azureOpenAiChatClient;
*/

    public String getAIResponse(String userMessage) {
        // You can enhance this to use chat history, etc.
        String aiResponse = "You said: " + userMessage;
        return aiResponse;//azureOpenAiChatClient.call(userMessage);
    }
}

