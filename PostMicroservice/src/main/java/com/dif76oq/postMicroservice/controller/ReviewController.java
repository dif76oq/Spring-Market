package com.dif76oq.postMicroservice.controller;

import com.dif76oq.postMicroservice.model.Review;
import com.dif76oq.postMicroservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @GetMapping
    public List<Review> findAllReviews() {
        return service.findAllReviews();
    }

    @PostMapping("save_review")
    public Review saveReview(@RequestBody Review review) {
        return service.saveReview(review);

    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable int id) {
        return service.findById(id);
    }

    @PutMapping("update_review")
    public Review updateReview(@RequestBody Review review) {
        return service.updateReview(review);
    }

    @DeleteMapping("delete_review/{id}")
    public void deleteReview(@PathVariable int id) {
        service.deleteReview(id);
    }
}
