package com.circleone.controller;

import com.circleone.service.AuthService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AlumniController {

    @Autowired
    private AuthService authService;

    private boolean isAuthorized(String sessionId) {
        return authService.isValidSession(sessionId);
    }

    @GetMapping("/alumni")
    public ResponseEntity<?> getAllAlumni(@CookieValue(value = "CIRCLEONE_SESSION", required = false) String sessionId) {
        if (!isAuthorized(sessionId)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        List<Map<String, Object>> alumni = new ArrayList<>();
        alumni.add(Map.of("id", 1, "name", "Priya Sharma", "graduationYear", 2021, "department", "Computer Science"));
        alumni.add(Map.of("id", 2, "name", "Rohan Gupta", "graduationYear", 2020, "department", "Mechanical Engineering"));
        alumni.add(Map.of("id", 3, "name", "Ananya Reddy", "graduationYear", 2022, "department", "Electronics"));
        alumni.add(Map.of("id", 4, "name", "Vikram Singh", "graduationYear", 2019, "department", "Civil Engineering"));
        
        return ResponseEntity.ok(alumni);
    }

    @GetMapping("/alumni/{id}")
    public ResponseEntity<?> getAlumniById(@PathVariable Long id, @CookieValue(value = "CIRCLEONE_SESSION", required = false) String sessionId) {
        if (!isAuthorized(sessionId)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        Map<String, Object> alumni = new HashMap<>();
        
        alumni.put("name", "ByteBenders");
        alumni.put("email", "ByteBenders@kiit.ac.in");
        alumni.put("graduationYear", "-");
        alumni.put("department", "Computer Science");
        alumni.put("currentCompany", "-");
        alumni.put("position", "-");
        
        return ResponseEntity.ok(alumni);
    }

    @GetMapping("/events")
    public ResponseEntity<?> getAllEvents(@CookieValue(value = "CIRCLEONE_SESSION", required = false) String sessionId) {
        if (!isAuthorized(sessionId)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        List<Map<String, Object>> events = new ArrayList<>();

        Map<String, Object> event1 = new HashMap<>();
        event1.put("id", 1);
        event1.put("title", "Fundraiser");
        event1.put("date", "2025-11-28");
        event1.put("location", "Bhubaneswar, Odisha");
        events.add(event1);

       
        return ResponseEntity.ok(events);
    }

}