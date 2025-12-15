package com.example.chatapp.dto;

import java.time.LocalDateTime;

public class MessageDto {

    private Long id;
    private Long senderId;
    private String senderUsername;
    private Long receiverId;
    private String receiverUsername;
    private String content;
    private LocalDateTime sendTime;

    public MessageDto(Long id,
                      Long senderId, String senderUsername,
                      Long receiverId, String receiverUsername,
                      String content, LocalDateTime sendTime) {

        this.id = id;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.receiverId = receiverId;
        this.receiverUsername = receiverUsername;
        this.content = content;
        this.sendTime = sendTime;
    }

    public Long getId() { return id; }
    public Long getSenderId() { return senderId; }
    public String getSenderUsername() { return senderUsername; }
    public Long getReceiverId() { return receiverId; }
    public String getReceiverUsername() { return receiverUsername; }
    public String getContent() { return content; }
    public LocalDateTime getSendTime() { return sendTime; }

    public void setId(Long id) { this.id = id; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }
    public void setSenderUsername(String senderUsername) { this.senderUsername = senderUsername; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
    public void setReceiverUsername(String receiverUsername) { this.receiverUsername = receiverUsername; }
    public void setContent(String content) { this.content = content; }
    public void setSendTime(LocalDateTime sendTime) { this.sendTime = sendTime; }
}
