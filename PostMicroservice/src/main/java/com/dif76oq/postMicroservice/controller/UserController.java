package com.dif76oq.postMicroservice.controller;

import com.dif76oq.postMicroservice.model.Post;
import com.dif76oq.postMicroservice.model.Review;
import com.dif76oq.postMicroservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;


    //TODO ВЫНЕСТИ В ФИЛЬТРЫ
    @GetMapping("/{id}/posts")
    public List<Post> findPostsByUserId(@PathVariable int id) {

        return service.findPostsByUserId(id);
    }

    @GetMapping("/{id}/reviews")
    public List<Review> findReviewByUserId(@PathVariable int id) {
        return service.findReviewsByUserId(id);
    }

}
