package com.dif76oq.userMicroservice.service.impl;

import com.dif76oq.userMicroservice.model.User;
import com.dif76oq.userMicroservice.repository.UserRepository;
import com.dif76oq.userMicroservice.service.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Value("${jwt.secret-key}")
    private String secretKey;
    private UserRepository repository;

    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }
    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByPhone(String phone) {
        return repository.findByPhone(phone).orElse(null);
    }
    @Override
    public String updateUser(User user, String token) {
        if (!repository.existsById(user.getId())) {
            return "User not found";
        }
        if (isTokenValid(user.getUsername(), token)) {
            repository.save(user);
            return "User updated successfully";
        } else {
            return "JWT validity shouldn't be trusted";
        }
    }

    @Override
    @Transactional
    public String deleteUser(int id, String token) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isPresent()) {
            String username = optionalUser.get().getUsername();
            if (isTokenValid(username, token)) {
                repository.deleteById(id);
                return "User deleted successfully";
            } else {
                return "JWT validity shouldn't be trusted";
            }
        } else {
            return "User not found";
        }
    }

    @Override
    public boolean isTokenValid(String username, String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        try {
            String str = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();
            return username.equals(str);
        } catch (JwtException e) {
            return false;
        }
    }

}
