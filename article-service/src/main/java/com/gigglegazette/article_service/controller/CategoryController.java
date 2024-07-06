package com.gigglegazette.article_service.controller;

import com.gigglegazette.article_service.model.Category;
import com.gigglegazette.article_service.repository.CategoryRepository;
import com.gigglegazette.article_service.util.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Create a new category.
     *
     * @param category The details of the category to be created.
     * @return A response entity with a success message if the category is created successfully.
     */
    @PostMapping
    public ResponseEntity<?>
    createCategory(@Valid @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors
            List<Map<String, String>> errorDetails = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                Map<String, String> errorDetail = new HashMap<>();
                errorDetail.put("field", error.getField());
                errorDetail.put("message", error.getDefaultMessage());
                errorDetails.add(errorDetail);
            }
            return ResponseEntity.status(400).body(
                    new CustomResponse<>("Validation Failed", errorDetails, false)
            );
        }
        try {
            Category savedCategory = categoryRepository.save(category);
            return new ResponseEntity<>(new CustomResponse<>("Category created successfully.", savedCategory, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error creating category: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch all categories.
     *
     * @return A response entity with the list of all categories and a success message.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<Category>>> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return new ResponseEntity<>(new CustomResponse<>("Categories retrieved successfully.", categories, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving categories: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch a category by its ID.
     *
     * @param id The ID of the category to be fetched.
     * @return A response entity with the category details if found, otherwise a not found message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Category>> getCategoryById(@PathVariable String id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            return category.map(value -> new ResponseEntity<>(new CustomResponse<>("Category retrieved successfully.", value, true), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new CustomResponse<>("Category not found.", null, false), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving category: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing category's attributes based on the request body.
     * Only the fields included in the request body will be updated.
     *
     * @param id              The ID of the category to be updated.
     * @param categoryDetails The updated category details.
     * @return A response entity indicating whether the update was successful or the category was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Category>> updateCategory(@PathVariable String id, @RequestBody Category categoryDetails) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                if (categoryDetails.getName() != null) category.setName(categoryDetails.getName());
                if (categoryDetails.getDescription() != null) category.setDescription(categoryDetails.getDescription());
                Category updatedCategory = categoryRepository.save(category);
                return new ResponseEntity<>(new CustomResponse<>("Category updated successfully.", updatedCategory, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Category not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error updating category: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete a category by its ID.
     *
     * @param id The ID of the category to be deleted.
     * @return A response entity indicating whether the deletion was successful or the category was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteCategory(@PathVariable String id) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (categoryOptional.isPresent()) {
                categoryRepository.deleteById(id);
                return new ResponseEntity<>(new CustomResponse<>("Category deleted successfully.", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Category not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error deleting category: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

