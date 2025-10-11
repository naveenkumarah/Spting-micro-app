package com.naveen.aichat;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/test")
    public Map<String, Object> test(@AuthenticationPrincipal UserDetails user, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "OAuth2 is working!");
        response.put("username", user != null ? user.getUsername() : principal.getName());
        response.put("authorities", user != null ? user.getAuthorities() : "N/A");
        return response;
    }
}

