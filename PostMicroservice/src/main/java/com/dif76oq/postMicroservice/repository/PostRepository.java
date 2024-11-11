package com.dif76oq.postMicroservice.repository;

import com.dif76oq.postMicroservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findById(int id);

    @Query("SELECT p FROM Post p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:categoryId IS NULL OR p.category.id = :categoryId)")
    List<Post> findPostsByFilters(
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("categoryId") Integer categoryId);
}
