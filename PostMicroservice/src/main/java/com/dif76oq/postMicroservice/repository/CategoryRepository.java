package com.dif76oq.postMicroservice.repository;

import com.dif76oq.postMicroservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findById(int id);
}
