package com.circleone.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
    
   
    private final Map<String, String> users = new HashMap<>();
    private final Map<String, String> activeSessions = new HashMap<>();
    
    public AuthService() {
      
        users.put("admin", "password123");
        users.put("alumni1", "pass123");
        users.put("ByteBenders", "1234");
    }
    
    public boolean validateCredentials(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
    
    public String createSession(String username) {
        String sessionId = UUID.randomUUID().toString();
        activeSessions.put(sessionId, username);
        return sessionId;
    }
    
    public boolean isValidSession(String sessionId) {
        return sessionId != null && activeSessions.containsKey(sessionId);
    }
    
    public String getUserFromSession(String sessionId) {
        return activeSessions.get(sessionId);
    }
    
    public void invalidateSession(String sessionId) {
        activeSessions.remove(sessionId);
    }
}