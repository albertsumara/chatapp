package com.example.chatapp.controller;

import com.example.chatapp.model.Message;
import com.example.chatapp.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    public static class MessageDto {

        private Long receiverId;
        private String content;

        public Long getReceiverId() { return receiverId; }
        public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDto dto, HttpSession session) {

        Long senderId = (Long) session.getAttribute("userId");

        if (senderId == null) return ResponseEntity.status(401).build();

        Message savedMessage = messageService.sendMessage(senderId, dto.getReceiverId(), dto.getContent());

        return ResponseEntity.ok(savedMessage);
    }
}
