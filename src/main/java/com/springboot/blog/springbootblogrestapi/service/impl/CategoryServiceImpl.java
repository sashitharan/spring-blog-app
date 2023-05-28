package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.CategoryDTO;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        Category savedCategory = categoryRepository.save(category);
        System.out.println(savedCategory.toString());

        //save the JPA entity into a Category DTO
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategory(Long categoryId) {
        Category categoryJPAObject = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return modelMapper.map(categoryJPAObject,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        List<Category> categoriesJPAEntities = categoryRepository.findAll();

        //convert the list of category jpa entities to a list of category DTO
        return categoriesJPAEntities.stream().map((category)->modelMapper.map(category,CategoryDTO.class)).collect(Collectors.toList());


    }

    @Override
    public CategoryDTO updateCategoryById(CategoryDTO categoryRequest, Long categoryId) {
//        retrieve category by id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        if(Objects.nonNull(categoryRequest.getId()) && !categoryId.equals(categoryRequest.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Category does not belongs to Category Id found in DB");
        }

        category.setDescription(categoryRequest.getDescription());
        category.setName(categoryRequest.getName());
//        category.setId(categoryId);
        Category saveAll = categoryRepository.save(category);
        return modelMapper.map(saveAll,CategoryDTO.class);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category categoryId = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(categoryId);
    }


}
