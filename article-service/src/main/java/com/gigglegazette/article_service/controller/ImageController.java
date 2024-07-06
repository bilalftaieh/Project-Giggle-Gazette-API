package com.gigglegazette.article_service.controller;

import com.gigglegazette.article_service.model.Image;
import com.gigglegazette.article_service.repository.ImageRepository;
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
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    /**
     * Create a new image.
     *
     * @param image The details of the image to be created.
     * @return A response entity with a success message if the image is created successfully.
     */
    @PostMapping
    public ResponseEntity<?>
    createImage(@Valid @RequestBody Image image, BindingResult result) {
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
            Image savedImage = imageRepository.save(image);
            return new ResponseEntity<>(new CustomResponse<>("Image created successfully.", savedImage, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error creating image: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch all images.
     *
     * @return A response entity with the list of all images and a success message.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<Image>>> getAllImages() {
        try {
            List<Image> images = imageRepository.findAll();
            return new ResponseEntity<>(new CustomResponse<>("Images retrieved successfully.", images, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving images: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch an image by its ID.
     *
     * @param id The ID of the image to be fetched.
     * @return A response entity with the image details if found, otherwise a not found message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Image>> getImageById(@PathVariable String id) {
        try {
            Optional<Image> image = imageRepository.findById(id);
            return image.map(value -> new ResponseEntity<>(new CustomResponse<>("Image retrieved successfully.", value, true), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(new CustomResponse<>("Image not found.", null, false), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving image: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing image's attributes based on the request body.
     * Only the fields included in the request body will be updated.
     *
     * @param id           The ID of the image to be updated.
     * @param imageDetails The updated image details.
     * @return A response entity indicating whether the update was successful or the image was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Image>> updateImage(@PathVariable String id, @RequestBody Image imageDetails) {
        try {
            Optional<Image> imageOptional = imageRepository.findById(id);
            if (imageOptional.isPresent()) {
                Image image = imageOptional.get();
                if (imageDetails.getUrl() != null) image.setUrl(imageDetails.getUrl());
                if (imageDetails.getName() != null) image.setName(imageDetails.getName());
                if (imageDetails.getDescription() != null) image.setDescription(imageDetails.getDescription());
                if (imageDetails.getContentType() != null) image.setContentType(imageDetails.getContentType());
                if (imageDetails.getSize() > 0) image.setSize(imageDetails.getSize());
                if (imageDetails.getWidth() > 0) image.setWidth(imageDetails.getWidth());
                if (imageDetails.getHeight() > 0) image.setHeight(imageDetails.getHeight());
                if (imageDetails.getArticle() != null) image.setArticle(imageDetails.getArticle());
                if (imageDetails.isPublic() != image.isPublic()) image.setPublic(imageDetails.isPublic());
                Image updatedImage = imageRepository.save(image);
                return new ResponseEntity<>(new CustomResponse<>("Image updated successfully.", updatedImage, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Image not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error updating image: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete an image by its ID.
     *
     * @param id The ID of the image to be deleted.
     * @return A response entity indicating whether the deletion was successful or the image was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteImage(@PathVariable String id) {
        try {
            Optional<Image> imageOptional = imageRepository.findById(id);
            if (imageOptional.isPresent()) {
                imageRepository.deleteById(id);
                return new ResponseEntity<>(new CustomResponse<>("Image deleted successfully.", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Image not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error deleting image: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch all images associated with a specific article.
     *
     * @param articleId The ID of the article for which images are to be retrieved.
     * @return A response entity with the list of images for the specified article.
     */
    @GetMapping("/article/{articleId}")
    public ResponseEntity<CustomResponse<List<Image>>> getImagesByArticleId(@PathVariable String articleId) {
        try {
            List<Image> images = imageRepository.findByArticle_Id(articleId);
            return new ResponseEntity<>(new CustomResponse<>("Images for the article retrieved successfully.", images, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving images for article: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
