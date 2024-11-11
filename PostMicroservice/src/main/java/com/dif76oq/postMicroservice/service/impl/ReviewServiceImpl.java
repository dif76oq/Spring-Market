package com.dif76oq.postMicroservice.service.impl;

import com.dif76oq.postMicroservice.model.Review;
import com.dif76oq.postMicroservice.model.User;
import com.dif76oq.postMicroservice.repository.ReviewRepository;
import com.dif76oq.postMicroservice.repository.UserRepository;
import com.dif76oq.postMicroservice.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;

    private final UserRepository userRepository;
    @Override
    public List<Review> findAllReviews() {
        return repository.findAll();
    }

    @Override
    public Review saveReview(Review review) {
        Review savedReview = repository.save(review);

        User seller = savedReview.getSeller();
        seller.setAvgRating( (seller.getAvgRating() * seller.getNumberOfReviews() + savedReview.getRating()) / (seller.getNumberOfReviews() + 1) );
        seller.setNumberOfReviews(seller.getNumberOfReviews() + 1);
        userRepository.save(seller);

        return savedReview;
    }

    @Override
    public Review findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Review updateReview(Review review) {
        int oldRate = repository.findById(review.getId().intValue()).getRating();
        Review updatedReview = repository.save(review);
        int delta = updatedReview.getRating()-oldRate;

        User seller = updatedReview.getSeller();
        seller.setAvgRating( (seller.getAvgRating() * seller.getNumberOfReviews() + delta) / seller.getNumberOfReviews() );
        userRepository.save(seller);

        return updatedReview;
    }

    @Override
    @Transactional
    public void deleteReview(int id) {
        Review deletingReview = repository.findById(id);

        User seller = deletingReview.getSeller();
        if (seller.getNumberOfReviews()==1) {
            seller.setAvgRating(0);
            seller.setNumberOfReviews(0);
        } else {
            seller.setAvgRating( (seller.getAvgRating() * seller.getNumberOfReviews() - deletingReview.getRating()) / (seller.getNumberOfReviews()-1) );
            seller.setNumberOfReviews(seller.getNumberOfReviews()-1);
        }
        userRepository.save(seller);

        repository.deleteById(id);

    }
}
