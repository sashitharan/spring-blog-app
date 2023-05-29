package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.payload.CategoryDTO;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "CRUD REST APIs For Categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //Build add category REST Api.
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){ // request body annotation will extract the JSON from the body and convert it into a java object.
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("getCategoryById/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long categoryId){
        CategoryDTO id = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(id,HttpStatus.CREATED);
    }

    @GetMapping("getAllCategories")
    public ResponseEntity<List> getAllCategories(){
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories,HttpStatus.CREATED);
    }

    @PutMapping("updateById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> updateCategoryById(@RequestBody CategoryDTO categoryDTO,@PathVariable("id") Long categoryId){
        CategoryDTO updatedCategory = categoryService.updateCategoryById(categoryDTO, categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.CREATED);
    }

    @DeleteMapping("deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategoryById(@PathVariable("id") Long id){
       categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.CREATED);
    }

}
