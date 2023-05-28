package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategory(Long categoryId);


    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategoryById(CategoryDTO categoryDTO, Long categoryId);

    public void deleteCategoryById(Long id);
}
