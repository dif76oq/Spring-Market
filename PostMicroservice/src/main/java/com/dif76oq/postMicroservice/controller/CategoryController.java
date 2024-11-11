package com.dif76oq.postMicroservice.controller;

import com.dif76oq.postMicroservice.model.Category;
import com.dif76oq.postMicroservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public List<Category> findAllCategories() {
        return service.findAllCategories();
    }

    @PostMapping("save_category")
    public Category saveCategory(@RequestBody Category category) {
        return service.saveCategory(category);
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable int id) {
        return service.findById(id);
    }

    @PutMapping("update_category")
    public Category updateCategory(@RequestBody Category category) {
        return service.updateCategory(category);
    }

    @DeleteMapping("delete_Category/{id}")
    public void deleteCategory(@PathVariable int id) {
        service.deleteCategory(id);
    }
}
