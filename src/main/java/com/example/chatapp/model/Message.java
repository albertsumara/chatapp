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

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "sent_At")
    private LocalDateTime sentAt;


    public Message(){
        this.sentAt = LocalDateTime.now();
    }

    public Long getId() { return id;}
    public void setId(Long id) {this.id = id;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;};

    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }

    public User getReceiver() { return receiver; }
    public void setReceiver(User receiver) { this.receiver = receiver; }

    public LocalDateTime getSendTime() { return sentAt; }
    public void setSendTime(LocalDateTime sentAt) { this.sentAt = sentAt; }


}
