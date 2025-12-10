package com.example.chatapp.service;


import com.example.chatapp.config.RabbitConfig;
import com.example.chatapp.model.Message;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageRepository messageRepository;


    public Message sendMessage(Long senderId, Long receiverId, String content){


        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setSendTime(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        rabbitTemplate.convertAndSend(RabbitConfig.USER_EVENTS_QUEUE, savedMessage);

        return savedMessage;
    }

}
