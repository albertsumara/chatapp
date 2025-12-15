package com.example.chatapp.service;


import com.example.chatapp.config.RabbitConfig;
import com.example.chatapp.dto.MessageDto;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.User;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageRepository messageRepository;

    public record MessagePayload(
            Long senderId,
            String senderUsername,
            Long receiverId,
            String receiverUsername,
            String content,
            LocalDateTime sendTime
    ) {}


    public Message sendMessage(User sender, User receiver, String content) {

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSendTime(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        MessagePayload payload = new MessagePayload(
                sender.getId(),
                sender.getUsername(),
                receiver.getId(),
                receiver.getUsername(),
                content,
                message.getSendTime()
        );

        rabbitTemplate.convertAndSend(RabbitConfig.USER_EVENTS_QUEUE, payload);

        return savedMessage;
    }

    public List<MessageDto> getConversation(Long user1Id, Long user2Id) {
        return messageRepository.findConversation(user1Id, user2Id)
                .stream()
                .map(m -> new MessageDto(
                        m.getId(),
                        m.getSender().getId(),
                        m.getSender().getUsername(),
                        m.getReceiver().getId(),
                        m.getReceiver().getUsername(),
                        m.getContent(),
                        m.getSendTime()
                ))
                .collect(Collectors.toList());
    }

}
