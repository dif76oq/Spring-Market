package com.dif76oq.userMicroservice.service.impl;

import com.dif76oq.userMicroservice.dto.LoginUserDto;
import com.dif76oq.userMicroservice.dto.RegisterUserDto;
import com.dif76oq.userMicroservice.dto.ResendUserDto;
import com.dif76oq.userMicroservice.dto.VerifyUserDto;
import com.dif76oq.userMicroservice.model.User;
import com.dif76oq.userMicroservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final KafkaTemplate<Integer, Integer> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public User signup(RegisterUserDto input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("This email is already used");
        }
        optionalUser = userRepository.findByUsername(input.getUsername());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("This username is already used");
        }
        User user = new User(input.getUsername(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(10));
        user.setEnabled(false);;
        User savedUser = userRepository.save(user);

        CompletableFuture<SendResult<Integer, Integer>> future = kafkaTemplate
                .send("user-registered-events-topic", savedUser.getId(), savedUser.getId());
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                LOGGER.error("failed to send message: {}", exception.getMessage());
            } else {
                LOGGER.info("Message successfully sent: {}", result.getRecordMetadata());
            }
        });

        future.join();

        return savedUser;
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow( () -> new RuntimeException("User not found"));
        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified. Please verify your account.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return user;
    }

    public void verifyUser(VerifyUserDto input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code has expired");
            }
            if (user.getVerificationCode().equals(input.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationCodeExpiresAt(null);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void resendVerificationCode(ResendUserDto input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(60));
            User savedUser = userRepository.save(user);

            CompletableFuture<SendResult<Integer, Integer>> future = kafkaTemplate
                    .send("user-registered-events-topic", savedUser.getId(), savedUser.getId());
            future.whenComplete((result, exception) -> {
                if (exception != null) {
                    LOGGER.error("failed to send message \"resending\": {}", exception.getMessage());
                } else {
                    LOGGER.info("Message \"resending\" successfully sent: {}", result.getRecordMetadata());
                }
            });

            future.join();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

}
