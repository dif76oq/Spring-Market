package com.dif76oq.postMicroservice.service.impl;

import com.dif76oq.postMicroservice.model.Category;
import com.dif76oq.postMicroservice.repository.CategoryRepository;
import com.dif76oq.postMicroservice.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    @Override
    public List<Category> findAllCategories() {
        return repository.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return repository.save(category);
    }

    @Override
    public Category findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Category updateCategory(Category category) {
        return repository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(int id) {
        repository.deleteById(id);
    }
}
