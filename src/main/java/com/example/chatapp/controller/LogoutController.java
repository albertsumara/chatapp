package com.example.chatapp.controller;

import com.example.chatapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LogoutController {

    @Autowired
    private UserService userService;

    public LogoutController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.badRequest().body("No user is logged in");
        }

        try {
            userService.logoutUser(userId);
            session.invalidate();
            return ResponseEntity.ok().body("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during logout");
        }
    }
}
