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
import com.app.restaurant.dto.master.RestaurantRequestDTO;
import com.app.restaurant.dto.master.RestaurantResponseDTO;
import com.app.restaurant.response.ApiResponse;
import com.app.restaurant.service.master.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<RestaurantResponseDTO>> createRestaurant(
            @RequestPart("data") RestaurantRequestDTO dto,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "banner", required = false) MultipartFile banner) {
        RestaurantResponseDTO created = restaurantService.createRestaurant(dto, logo, banner);

        return ResponseEntity.ok(ApiResponse.<RestaurantResponseDTO>builder()
                .status(true)
                .message(MessageConstants.CREATED_SUCCESSFULLY)
                .error(null)
                .data(created)
                .build());
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<RestaurantResponseDTO>> updateRestaurant(
            @PathVariable Integer restaurantId,
            @RequestPart("data") RestaurantRequestDTO dto,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "banner", required = false) MultipartFile banner) {
        RestaurantResponseDTO updated = restaurantService.updateRestaurant(restaurantId, dto, logo, banner);

        return ResponseEntity.ok(ApiResponse.<RestaurantResponseDTO>builder()
                .status(true)
                .message(MessageConstants.UPDATED_SUCCESSFULLY)
                .error(null)
                .data(updated)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<RestaurantResponseDTO>>> getRestaurants(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "false") Boolean unpaged,
            @ParameterObject Pageable pageable) {

        Page<RestaurantResponseDTO> restaurants = restaurantService.getAllRestaurants(status, search, unpaged, pageable);

        return ResponseEntity.ok(ApiResponse.<Page<RestaurantResponseDTO>>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(restaurants)
                .build());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse<RestaurantResponseDTO>> getRestaurantById(@PathVariable Integer restaurantId) {
        RestaurantResponseDTO restaurant = restaurantService.getRestaurantById(restaurantId);

        return ResponseEntity.ok(ApiResponse.<RestaurantResponseDTO>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(restaurant)
                .build());
    }

    @PatchMapping("/{restaurantId}/toggle-status")
    public ResponseEntity<ApiResponse<String>> toggleRestaurantStatus(@PathVariable Integer restaurantId) {
        String msg = restaurantService.toggleRestaurantStatus(restaurantId);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(true)
                .message(MessageConstants.TOGGLED_SUCCESSFULLY)
                .error(null)
                .data(msg)
                .build());
    }
}
