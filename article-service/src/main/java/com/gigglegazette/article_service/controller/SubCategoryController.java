package com.gigglegazette.article_service.controller;

import com.gigglegazette.article_service.model.SubCategory;
import com.gigglegazette.article_service.repository.SubCategoryRepository;
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
@RequestMapping("/subcategories")
public class SubCategoryController {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    /**
     * Create a new subcategory.
     *
     * @param subCategory The details of the subcategory to be created.
     * @return A response entity with a success message if the subcategory is created successfully.
     */
    @PostMapping
    public ResponseEntity<?>
    createSubCategory(@Valid @RequestBody SubCategory subCategory
            , BindingResult result) {
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
            SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
            return new ResponseEntity<>(new CustomResponse<>("SubCategory created successfully.", savedSubCategory, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error creating subcategory: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch all subcategories.
     *
     * @return A response entity with the list of all subcategories and a success message.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<SubCategory>>> getAllSubCategories() {
        try {
            List<SubCategory> subCategories = subCategoryRepository.findAll();
            return new ResponseEntity<>(new CustomResponse<>("SubCategories retrieved successfully.", subCategories, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving subcategories: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch a subcategory by its ID.
     *
     * @param id The ID of the subcategory to be fetched.
     * @return A response entity with the subcategory details if found, otherwise a not found message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<SubCategory>> getSubCategoryById(@PathVariable String id) {
        try {
            Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
            return subCategory.map(value -> new ResponseEntity<>(new CustomResponse<>("SubCategory retrieved successfully.", value, true), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(new CustomResponse<>("SubCategory not found.", null, false), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving subcategory: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing subcategory's attributes based on the request body.
     * Only the fields included in the request body will be updated.
     *
     * @param id                 The ID of the subcategory to be updated.
     * @param subCategoryDetails The updated subcategory details.
     * @return A response entity indicating whether the update was successful or the subcategory was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<SubCategory>> updateSubCategory(@PathVariable String id, @RequestBody SubCategory subCategoryDetails) {
        try {
            Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
            if (subCategoryOptional.isPresent()) {
                SubCategory subCategory = subCategoryOptional.get();
                if (subCategoryDetails.getName() != null) subCategory.setName(subCategoryDetails.getName());
                if (subCategoryDetails.getDescription() != null)
                    subCategory.setDescription(subCategoryDetails.getDescription());
                if (subCategoryDetails.getParentCategory() != null)
                    subCategory.setParentCategory(subCategoryDetails.getParentCategory());
                SubCategory updatedSubCategory = subCategoryRepository.save(subCategory);
                return new ResponseEntity<>(new CustomResponse<>("SubCategory updated successfully.", updatedSubCategory, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("SubCategory not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error updating subcategory: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete a subcategory by its ID.
     *
     * @param id The ID of the subcategory to be deleted.
     * @return A response entity indicating whether the deletion was successful or the subcategory was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteSubCategory(@PathVariable String id) {
        try {
            Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
            if (subCategoryOptional.isPresent()) {
                subCategoryRepository.deleteById(id);
                return new ResponseEntity<>(new CustomResponse<>("SubCategory deleted successfully.", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("SubCategory not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error deleting subcategory: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
