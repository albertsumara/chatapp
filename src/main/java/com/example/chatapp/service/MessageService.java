package com.example.chatapp.service;


import com.example.chatapp.config.RabbitConfig;
import com.example.chatapp.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMessage(String message){

        System.out.println(message);
        rabbitTemplate.convertAndSend(RabbitConfig.USER_EVENTS_QUEUE, message);

    }

}
