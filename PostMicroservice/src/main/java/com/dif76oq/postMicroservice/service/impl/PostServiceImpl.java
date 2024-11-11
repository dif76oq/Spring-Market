package com.dif76oq.postMicroservice.service.impl;

import com.dif76oq.postMicroservice.model.Post;
import com.dif76oq.postMicroservice.repository.PostRepository;
import com.dif76oq.postMicroservice.service.PostService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.JwtException;

import java.security.Key;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final PostRepository repository;
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Post> findAllPosts() {
        return repository.findAll();
    }

    @Override
    public List<Post> findPostsByFilters(String name, Double minPrice, Double maxPrice, Integer categoryId) {
        return repository.findPostsByFilters(name, minPrice, maxPrice, categoryId);
    }

    @Override
    public ResponseEntity<Post> savePost(Post post, String token) {
        if (isTokenValid(post.getUser().getUsername(), token)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(post));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); //"Invalid or expired token"
        }
    }

    //TODO OPTIONAL
    @Override
    public Optional<Post> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public String updatePost(Post post, String token) {
        if (!repository.existsById(post.getId())) {
            return "Post not found";
        }
        if (isTokenValid(post.getUser().getUsername(), token)) {
            repository.save(post);
            return "Post updated successfully";
        } else {
            return "JWT validity shouldn't be trusted";
        }
    }

    @Override
    @Transactional
    public String deletePost(int id, String token) {
        Optional<Post> optionalPost = findById(id);
        if (optionalPost.isPresent()) {
            String username = optionalPost.get().getUser().getUsername();
            if (isTokenValid(username, token)) {
                repository.deleteById(id);
                return "Post deleted successfully";
            } else {
                return "JWT validity shouldn't be trusted";
            }
        } else {
            return "Post not found";
        }
    }

    @Override
    public boolean isTokenValid(String username, String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        try {
            String str = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();
            return username.equals(str);
        } catch (JwtException e) {
            return false;
        }
    }
}
