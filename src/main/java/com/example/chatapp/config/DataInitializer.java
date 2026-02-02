package com.example.chatapp.config;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.User;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, MessageRepository messageRepository) {
        return args -> {
            User user1 = userRepository.findByUsername("user1")
                    .orElseGet(() -> userRepository.save(createUser("user1", "user1@example.com", "user1")));

            User user2 = userRepository.findByUsername("user2")
                    .orElseGet(() -> userRepository.save(createUser("user2", "user2@example.com", "user2")));

            User user3 = userRepository.findByUsername("user3")
                    .orElseGet(() -> userRepository.save(createUser("user3", "user3@example.com", "user3")));

            if (messageRepository.count() == 0) {
                saveMessage("Hey user2, how's it going?", user1, user2, messageRepository);
                saveMessage("Hi user1! I'm good, thanks. You?", user2, user1, messageRepository);
                saveMessage("Hello user3! Welcome to the chat.", user1, user3, messageRepository);
                saveMessage("Thanks user1! Glad to be here.", user3, user1, messageRepository);
                saveMessage("user2, wanna play a game later?", user3, user2, messageRepository);
                saveMessage("Sure, let's do it at 6 PM.", user2, user3, messageRepository);
            }

            System.out.println("✅ Sample users and messages initialized.");
        };
    }

    private User createUser(String username, String email, String password) {
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(password);
        u.setCreatedAt(LocalDateTime.now());
        return u;
    }

    private void saveMessage(String content, User sender, User receiver, MessageRepository messageRepository) {
        Message msg = new Message();
        msg.setContent(content);
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setSendTime(LocalDateTime.now());
        messageRepository.save(msg);
    }
}
