package com.example.chatapp.config;

import com.example.chatapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ResetLoggedStatus implements CommandLineRunner {

    private final UserRepository userRepository;

    public ResetLoggedStatus(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.resetAllLoggedStatus();
    }
}
