package com.dif76oq.userMicroservice.repository;

import com.dif76oq.userMicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByVerificationCode(String verificationCode);

    Optional<User> findByPhone(String phone);


}
