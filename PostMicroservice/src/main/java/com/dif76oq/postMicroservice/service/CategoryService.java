package com.dif76oq.postMicroservice.service;

import com.dif76oq.postMicroservice.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category saveCategory(Category category);
    Category findById(int id);
    Category updateCategory(Category category);
    void deleteCategory(int id);
}
