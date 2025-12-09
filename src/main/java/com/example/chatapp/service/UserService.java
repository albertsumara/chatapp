package com.example.chatapp.service;

import com.example.chatapp.model.User;
import com.example.chatapp.repository.UserRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;


    public User registerUser(String username, String email, String password) throws Exception {

        if (userRepository.existsByUsername(username)) {
            throw new Exception("Username already exists.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email already exists.");
        }

        if (username.length() < 5){
            throw new Exception("Username is too short. Minimum 5 characters.");
        }

        validateEmail(email);

        if (password.length() < 5){
            throw new Exception("Password is too short. Minimum 5 characters.");
        }



        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);

        amqpTemplate.convertAndSend("user.events", "REGISTERED:" + saved.getUsername());

        return saved;
    }

    public User loginUser(String username, String password) throws Exception {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {
            throw new Exception("Wrong username.");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            throw new Exception("Wrong password.");
        }

        user.setLogged(true);
        user.setLastLogin(LocalDateTime.now());


        return userRepository.save(user);

    }

    public User logoutUser(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("User not founded.");
        }

        User user = optionalUser.get();
        user.setLogged(false);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return  userRepository.findAll();
    }


    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void validateEmail(String email) throws Exception {

        if (email == null || email.isEmpty()) {
            throw new Exception("Invalid e-mail format. Can't be empty.");
        }

        int counter=0;
        boolean sign_detected = false;
        boolean dot_detected = false;

        for (char c : email.toCharArray()){

            counter++;

            if (c == '@') {

                if(dot_detected){
                    throw new Exception("Invalid e-mail format.");
                }

                if(counter < 4) {
                    throw new Exception("Invalid e-mail format.");
                }

                sign_detected = true;
                counter = 0;
                continue;

            }
            if (c == '.'){
                if (!sign_detected){
                    throw new Exception("Invalid e-mail format.");
                }
                if(counter < 2) {
                    throw new Exception("Invalid e-mail format.");
                }
                dot_detected = true;
                counter = 0;
                continue;
            }
            if (!Character.isLetterOrDigit(c))  {
                throw new Exception("Invalid e-mail format.");
            }
        }
        if (!dot_detected){
            throw new Exception("Invalid e-mail format.");
        }
        if (counter < 2){
            throw new Exception("Invalid e-mail format.");
        }
    }
}
