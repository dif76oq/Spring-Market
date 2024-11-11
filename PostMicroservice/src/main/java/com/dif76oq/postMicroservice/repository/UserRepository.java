package com.dif76oq.postMicroservice.repository;

import com.dif76oq.postMicroservice.model.Post;
import com.dif76oq.postMicroservice.model.Review;
import com.dif76oq.postMicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);

    @Query("SELECT u.posts FROM User u WHERE u.id = :id")
    List<Post> findPostsByUserId(@Param("id") int id);

    @Query("SELECT u.reviews FROM User u WHERE u.id = :id")
    List<Review> findReviewsByUserId(@Param("id") int id);

}
