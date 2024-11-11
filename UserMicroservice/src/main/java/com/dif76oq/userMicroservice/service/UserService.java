package com.dif76oq.userMicroservice.service;

import com.dif76oq.userMicroservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    Optional<User> findById(int id);
    User findByEmail(String email);
    User findByPhone(String phone);
    String updateUser(User user, String token);
    String deleteUser(int id, String token);
    boolean isTokenValid(String username, String token);
}
