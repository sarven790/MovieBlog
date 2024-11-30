package com.example.filmblogproject.interfaceAdapters.controller;

import com.example.filmblogproject.application.service.CategoryService;
import com.example.filmblogproject.domain.entity.Category;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.CategoryRequest;
import com.example.filmblogproject.interfaceAdapters.handler.DataException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @QueryMapping
    public List<Category> getAllCategory() throws DataException {
        return categoryService.getAllCategory();
    }

    @MutationMapping
    public Category createCategory(@Argument CategoryRequest categoryRequest) throws DataException {
        return categoryService.createCategory(categoryRequest);
    }

}
