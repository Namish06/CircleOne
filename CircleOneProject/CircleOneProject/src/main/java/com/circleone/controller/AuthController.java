package com.circleone.controller;

import com.circleone.dto.LoginRequest;
import com.circleone.dto.LoginResponse;
import com.circleone.service.AuthService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        if (authService.validateCredentials(request.getUsername(), request.getPassword())) {
            String sessionId = authService.createSession(request.getUsername());
            
            Cookie sessionCookie = new Cookie("CIRCLEONE_SESSION", sessionId);
            sessionCookie.setMaxAge(3600); 
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);
            
            return ResponseEntity.ok(new LoginResponse(true, "Login successful", sessionId));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse(false, "Invalid username or password"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout(@CookieValue(value = "CIRCLEONE_SESSION", required = false) String sessionId, HttpServletResponse response) {
        if (sessionId != null) {
            authService.invalidateSession(sessionId);
        }
                Cookie sessionCookie = new Cookie("CIRCLEONE_SESSION", "");
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
        
        return ResponseEntity.ok(new LoginResponse(true, "Logged out successfully"));
    }

    @GetMapping("/validate")
    public ResponseEntity<LoginResponse> validateSession(@CookieValue(value = "CIRCLEONE_SESSION", required = false) String sessionId) {
        if (authService.isValidSession(sessionId)) {
            String username = authService.getUserFromSession(sessionId);
            return ResponseEntity.ok(new LoginResponse(true, "Session valid for user: " + username));
        } else {
            return ResponseEntity.ok(new LoginResponse(false, "Invalid session"));
        }
    }
}