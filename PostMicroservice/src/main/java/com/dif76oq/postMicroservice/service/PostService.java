package com.dif76oq.postMicroservice.service;

import com.dif76oq.postMicroservice.model.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAllPosts();
    List<Post> findPostsByFilters(String name, Double minPrice, Double maxPrice, Integer categoryId);
    ResponseEntity<Post> savePost(Post post, String token);
    Optional<Post> findById(int id);
    String updatePost(Post post, String token);
    String deletePost(int id, String token);
    public boolean  isTokenValid(String username,String token);
}
