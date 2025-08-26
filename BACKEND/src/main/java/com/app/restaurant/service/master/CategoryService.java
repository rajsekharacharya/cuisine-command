package com.app.restaurant.service.master;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.app.restaurant.dto.master.CategoryRequestDTO;
import com.app.restaurant.dto.master.CategoryResponseDTO;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO dto, MultipartFile image);

    CategoryResponseDTO updateCategory(Integer categoryId, CategoryRequestDTO dto, MultipartFile image);

    Page<CategoryResponseDTO> getAllCategories(Boolean status, String search, Boolean unpaged, Pageable pageable);

    CategoryResponseDTO getCategoryById(Integer categoryId);

    String toggleCategoryStatus(Integer categoryId);
}
