package com.dif76oq.postMicroservice.controller;

import com.dif76oq.postMicroservice.model.Post;
import com.dif76oq.postMicroservice.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping
    public List<Post> findAllPosts() {
        return service.findAllPosts();
    }

    @GetMapping("/filter")
    public List<Post> findPostsByFilters(

            //TODO lots of categories

            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer categoryId) {

        return service.findPostsByFilters(name, minPrice, maxPrice, categoryId);
    }

    @PostMapping("save_post")
    public ResponseEntity<Post> savePost(@RequestBody Post post, @RequestHeader("Authorization") String authorizationHeader) {
        return service.savePost(post, getToken(authorizationHeader));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable int id) {
        Optional<Post> postOptional = service.findById(id);

        return postOptional
                .map(post -> ResponseEntity.ok(post))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("update_post")
    public String updatePost(@RequestBody Post post, @RequestHeader("Authorization") String authorizationHeader) {
        return service.updatePost(post, getToken(authorizationHeader));
    }

    @DeleteMapping("delete_post/{id}")
    public String deletePost(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        return service.deletePost(id, getToken(authorizationHeader));
    }

    private String getToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header must be provided with Bearer token");
        }
        return header.replace("Bearer ", "").trim();
    }

}