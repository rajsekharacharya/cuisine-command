package com.app.restaurant.controller.master;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.master.CategoryRequestDTO;
import com.app.restaurant.dto.master.CategoryResponseDTO;
import com.app.restaurant.response.ApiResponse;
import com.app.restaurant.service.master.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(
            @RequestPart("data") CategoryRequestDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        CategoryResponseDTO created = categoryService.createCategory(dto, image);

        return ResponseEntity.ok(ApiResponse.<CategoryResponseDTO>builder()
                .status(true)
                .message(MessageConstants.CREATED_SUCCESSFULLY)
                .error(null)
                .data(created)
                .build());
    }

    @PutMapping(value = "/{categoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(
            @PathVariable Integer categoryId,
            @RequestPart("data") CategoryRequestDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        CategoryResponseDTO updated = categoryService.updateCategory(categoryId, dto, image);

        return ResponseEntity.ok(ApiResponse.<CategoryResponseDTO>builder()
                .status(true)
                .message(MessageConstants.UPDATED_SUCCESSFULLY)
                .error(null)
                .data(updated)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CategoryResponseDTO>>> getCategories(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "false") Boolean unpaged,
            @ParameterObject Pageable pageable) {

        Page<CategoryResponseDTO> categories = categoryService.getAllCategories(status, search, unpaged, pageable);

        return ResponseEntity.ok(ApiResponse.<Page<CategoryResponseDTO>>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(categories)
                .build());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategoryById(@PathVariable Integer categoryId) {
        CategoryResponseDTO category = categoryService.getCategoryById(categoryId);

        return ResponseEntity.ok(ApiResponse.<CategoryResponseDTO>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(category)
                .build());
    }

    @PatchMapping("/{categoryId}/toggle-status")
    public ResponseEntity<ApiResponse<String>> toggleCategoryStatus(@PathVariable Integer categoryId) {
        String msg = categoryService.toggleCategoryStatus(categoryId);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(true)
                .message(MessageConstants.TOGGLED_SUCCESSFULLY)
                .error(null)
                .data(msg)
                .build());
    }
}