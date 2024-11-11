package com.dif76oq.postMicroservice.service;

import com.dif76oq.postMicroservice.model.Review;

import java.util.List;


public interface ReviewService {
    List<Review> findAllReviews();
    Review saveReview(Review review);
    Review findById(int id);
    Review updateReview(Review review);
    void deleteReview(int id);
}
