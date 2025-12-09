package com.example.chatapp.controller;

import com.example.chatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public String sendMessage(@RequestBody String message) {
        messageService.sendMessage(message);
        return "Sent: " + message;
    }
}
