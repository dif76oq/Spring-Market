package com.dif76oq.postMicroservice.repository;


import com.dif76oq.postMicroservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findById(int id);
}
