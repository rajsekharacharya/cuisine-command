package com.app.restaurant.serviceimpl.master;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.restaurant.configuration.FileUploader;
import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.master.CategoryRequestDTO;
import com.app.restaurant.dto.master.CategoryResponseDTO;
import com.app.restaurant.exception.ResourceNotFoundException;
import com.app.restaurant.model.master.Category;
import com.app.restaurant.repository.master.CategoryRepository;
import com.app.restaurant.service.master.CategoryService;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final String REPOSITORY_FOLDER = "Category";

    private final CategoryRepository categoryRepository;
    private final FileUploader fileUploader;

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto, MultipartFile image) {
        log.info("Creating a new category with name: {}", dto.getName());
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setStatus(dto.getStatus());

        if (image != null && !image.isEmpty()) {
            category.setImage(fileUploader.uploadFile(image, REPOSITORY_FOLDER));
            log.debug("Image uploaded successfully for category: {}", dto.getName());
        }

        Category savedCategory = categoryRepository.save(category);
        log.info(MessageConstants.CREATED_SUCCESSFULLY);
        return toResponseDTO(savedCategory);
    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(Integer categoryId, CategoryRequestDTO dto, MultipartFile image) {
        log.info("Updating category with ID: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setStatus(dto.getStatus() != null ? dto.getStatus() : category.isStatus());

        if (image != null && !image.isEmpty()) {
            category.setImage(fileUploader.uploadFile(image, REPOSITORY_FOLDER));
            log.debug("Updated image for category ID: {}", categoryId);
        }

        Category savedCategory = categoryRepository.save(category);
        log.info(MessageConstants.UPDATED_SUCCESSFULLY);
        return toResponseDTO(savedCategory);
    }

    @Override
    public Page<CategoryResponseDTO> getAllCategories(Boolean status, String search, Boolean unpaged,
            Pageable pageable) {
        log.info("Fetching all categories with status: {}, search: {}", status, search);
        Specification<Category> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (search != null && !search.trim().isEmpty()) {
                String searchLower = "%" + search.toLowerCase() + "%";
                Predicate namePredicate = cb.like(cb.lower(root.get("name")), searchLower);
                Predicate descPredicate = cb.like(cb.lower(root.get("description")), searchLower);

                // Combine both using OR
                Predicate searchPredicate = cb.or(namePredicate, descPredicate);

                // Combine with main predicate using AND
                predicate = cb.and(predicate, searchPredicate);
            }
            return predicate;
        };

        if (Boolean.TRUE.equals(unpaged)) {
            List<Category> all = categoryRepository.findAll(spec);
            List<CategoryResponseDTO> dtos = all.stream().map(this::toResponseDTO).toList();
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return new PageImpl<>(dtos);
        } else {
            Page<Category> page = categoryRepository.findAll(spec, pageable);
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return page.map(this::toResponseDTO);
        }
    }

    @Override
    public CategoryResponseDTO getCategoryById(Integer categoryId) {
        log.info("Fetching category with ID: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        log.info(MessageConstants.FETCHED_SUCCESSFULLY);
        return toResponseDTO(category);
    }

    @Override
    @Transactional
    public String toggleCategoryStatus(Integer categoryId) {
        log.info("Toggling status for category with ID: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));

        category.setStatus(!category.isStatus());
        categoryRepository.save(category);

        String message = category.isStatus()
                ? "Category activated successfully."
                : "Category deactivated successfully.";
        log.info(message);
        return message;
    }

    private CategoryResponseDTO toResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImage(category.getImage());
        dto.setStatus(category.isStatus());
        return dto;
    }
}