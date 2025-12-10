package com.example.chatapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(name = "sender_id")
    private long senderId;

    @Column(name = "receiver_id")
    private long receiverId;

    @Column(name = "sent_At")
    private LocalDateTime sentAt;


    public Message(){
        this.sentAt = LocalDateTime.now();
    }

    public Long getId() { return id;}
    public void setId(Long id) {this.id = id;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;};

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long sender) { this.senderId = sender; }

    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiver) { this.receiverId = receiver; }

    public LocalDateTime getSendTime() { return sentAt; }
    public void setSendTime(LocalDateTime sentAt) { this.sentAt = sentAt; }


}
