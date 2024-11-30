package com.example.filmblogproject.application.service;

import com.example.filmblogproject.domain.entity.Category;
import com.example.filmblogproject.domain.repository.CategoryRepository;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    public Category getMovieFromCategoryId(Long id) {
        Optional<Category> optCategory = categoryRepository.findById(id);
        Category category = new Category();
        if (optCategory.isPresent()) {
            category = optCategory.get();
        }
        return category;
    }

}
