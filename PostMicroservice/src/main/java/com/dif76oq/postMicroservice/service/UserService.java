package com.dif76oq.postMicroservice.service;

import com.dif76oq.postMicroservice.model.Post;
import com.dif76oq.postMicroservice.model.Review;
import com.dif76oq.postMicroservice.model.User;

import java.util.List;

public interface UserService {

    User findById(int id);
    List<Post> findPostsByUserId(int id);
    List<Review> findReviewsByUserId(int id);

}
