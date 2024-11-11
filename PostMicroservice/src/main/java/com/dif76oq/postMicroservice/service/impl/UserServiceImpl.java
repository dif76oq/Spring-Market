package com.dif76oq.postMicroservice.service.impl;

import com.dif76oq.postMicroservice.model.Post;
import com.dif76oq.postMicroservice.model.Review;
import com.dif76oq.postMicroservice.model.User;
import com.dif76oq.postMicroservice.repository.UserRepository;
import com.dif76oq.postMicroservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public User findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Post> findPostsByUserId(int id) {
        return  repository.findPostsByUserId(id);
    }

    @Override
    public List<Review> findReviewsByUserId(int id) {
        return  repository.findReviewsByUserId(id);
    }


}
