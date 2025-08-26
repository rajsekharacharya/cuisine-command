package com.app.restaurant.service.master;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.app.restaurant.dto.master.RestaurantRequestDTO;
import com.app.restaurant.dto.master.RestaurantResponseDTO;

public interface RestaurantService {
    RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto, MultipartFile logo, MultipartFile banner);

    RestaurantResponseDTO updateRestaurant(Integer restaurantId, RestaurantRequestDTO dto, MultipartFile logo,
            MultipartFile banner);

    Page<RestaurantResponseDTO> getAllRestaurants(Boolean status, String search, Boolean unpaged, Pageable pageable);

    RestaurantResponseDTO getRestaurantById(Integer restaurantId);

    String toggleRestaurantStatus(Integer restaurantId);
}