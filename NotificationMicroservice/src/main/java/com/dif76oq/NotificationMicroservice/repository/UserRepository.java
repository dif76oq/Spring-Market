package com.dif76oq.NotificationMicroservice.repository;

import com.dif76oq.NotificationMicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer userId);

}