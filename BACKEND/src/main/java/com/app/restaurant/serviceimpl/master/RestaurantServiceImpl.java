package com.app.restaurant.serviceimpl.master;

import com.app.restaurant.configuration.FileUploader;
import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.master.RestaurantRequestDTO;
import com.app.restaurant.dto.master.RestaurantResponseDTO;
import com.app.restaurant.exception.ResourceNotFoundException;
import com.app.restaurant.model.master.Restaurant;
import com.app.restaurant.repository.master.RestaurantRepository;
import com.app.restaurant.service.master.RestaurantService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private static final String REPOSITORY_FOLDER = "Restaurant";

    private final RestaurantRepository restaurantRepository;
    private final FileUploader fileUploader;

    @Override
    @Transactional
    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto, MultipartFile logo, MultipartFile banner) {
        log.info("Creating a new restaurant with name: {}", dto.getName());
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setCuisine(dto.getCuisine());
        restaurant.setDescription(dto.getDescription());
        restaurant.setAddress(dto.getAddress());
        restaurant.setContact(dto.getContact());
        restaurant.setStatus(dto.getStatus());

        if (logo != null && !logo.isEmpty()) {
            restaurant.setLogo(fileUploader.uploadFile(logo, REPOSITORY_FOLDER));
            log.debug("Logo uploaded successfully for restaurant: {}", dto.getName());
        }
        if (banner != null && !banner.isEmpty()) {
            restaurant.setBanner(fileUploader.uploadFile(banner, REPOSITORY_FOLDER));
            log.debug("Banner uploaded successfully for restaurant: {}", dto.getName());
        }

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        log.info(MessageConstants.CREATED_SUCCESSFULLY);
        return toResponseDTO(savedRestaurant);
    }

    @Override
    @Transactional
    public RestaurantResponseDTO updateRestaurant(Integer restaurantId, RestaurantRequestDTO dto, MultipartFile logo,
            MultipartFile banner) {
        log.info("Updating restaurant with ID: {}", restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));

        restaurant.setName(dto.getName());
        restaurant.setCuisine(dto.getCuisine());
        restaurant.setDescription(dto.getDescription());
        restaurant.setAddress(dto.getAddress());
        restaurant.setContact(dto.getContact());
        restaurant.setStatus(dto.getStatus() != null ? dto.getStatus() : restaurant.isStatus());

        if (logo != null && !logo.isEmpty()) {
            restaurant.setLogo(fileUploader.uploadFile(logo, REPOSITORY_FOLDER));
            log.debug("Updated logo for restaurant ID: {}", restaurantId);
        }
        if (banner != null && !banner.isEmpty()) {
            restaurant.setBanner(fileUploader.uploadFile(banner, REPOSITORY_FOLDER));
            log.debug("Updated banner for restaurant ID: {}", restaurantId);
        }

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        log.info(MessageConstants.UPDATED_SUCCESSFULLY);
        return toResponseDTO(savedRestaurant);
    }

    @Override
    public Page<RestaurantResponseDTO> getAllRestaurants(Boolean status, String search, Boolean unpaged,
            Pageable pageable) {
        log.info("Fetching all restaurants with status: {}, search: {}", status, search);
        Specification<Restaurant> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                Predicate namePredicate = cb.like(cb.lower(root.get("name")), searchPattern);
                Predicate cuisinePredicate = cb.like(cb.lower(root.get("cuisine")), searchPattern);
                Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), searchPattern);
                Predicate contactPredicate = cb.like(cb.lower(root.get("contact")), searchPattern);

                Predicate searchPredicate = cb.or(namePredicate, cuisinePredicate, descriptionPredicate,
                        contactPredicate);
                predicate = cb.and(predicate, searchPredicate);
            }

            return predicate;
        };

        if (Boolean.TRUE.equals(unpaged)) {
            List<Restaurant> all = restaurantRepository.findAll(spec);
            List<RestaurantResponseDTO> dtos = all.stream().map(this::toResponseDTO).toList();
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return new PageImpl<>(dtos);
        } else {
            Page<Restaurant> page = restaurantRepository.findAll(spec, pageable);
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return page.map(this::toResponseDTO);
        }
    }

    @Override
    public RestaurantResponseDTO getRestaurantById(Integer restaurantId) {
        log.info("Fetching restaurant with ID: {}", restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        log.info(MessageConstants.FETCHED_SUCCESSFULLY);
        return toResponseDTO(restaurant);
    }

    @Override
    @Transactional
    public String toggleRestaurantStatus(Integer restaurantId) {
        log.info("Toggling status for restaurant with ID: {}", restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));

        restaurant.setStatus(!restaurant.isStatus());
        restaurantRepository.save(restaurant);

        String message = restaurant.isStatus()
                ? "Restaurant activated successfully."
                : "Restaurant deactivated successfully.";
        log.info(message);
        return message;
    }

    private RestaurantResponseDTO toResponseDTO(Restaurant restaurant) {
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setCuisine(restaurant.getCuisine());
        dto.setDescription(restaurant.getDescription());
        dto.setAddress(restaurant.getAddress());
        dto.setContact(restaurant.getContact());
        dto.setLogo(restaurant.getLogo());
        dto.setBanner(restaurant.getBanner());
        dto.setStatus(restaurant.isStatus());
        return dto;
    }
}
