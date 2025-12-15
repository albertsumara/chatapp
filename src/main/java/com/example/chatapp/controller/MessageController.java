package com.example.chatapp.controller;

import com.example.chatapp.dto.MessageDto;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.User;
import com.example.chatapp.service.MessageService;
import com.example.chatapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

//    public static class MessageDto {
//        private Long receiverId;
//        private String content;
//
//        public Long getReceiverId() { return receiverId; }
//        public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
//
//        public String getContent() { return content; }
//        public void setContent(String content) { this.content = content; }
//    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDto dto, HttpSession session) {

        Long senderId = (Long) session.getAttribute("userId");

        if (senderId == null) return ResponseEntity.status(401).build();

        Optional<User> senderOpt = userService.getUserById(senderId);
        Optional<User> receiverOpt = userService.getUserById(dto.getReceiverId());

        if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        User sender = senderOpt.get();
        User receiver = receiverOpt.get();

        Message savedMessage = messageService.sendMessage(sender, receiver, dto.getContent());

        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<MessageDto>> getConversation(
            @RequestParam Long receiverId,
            HttpSession session) {

        Long senderId = (Long) session.getAttribute("userId");
        if (senderId == null) return ResponseEntity.status(401).build();

        List<MessageDto> conversation = messageService.getConversation(senderId, receiverId);
        return ResponseEntity.ok(conversation);
    }

}
