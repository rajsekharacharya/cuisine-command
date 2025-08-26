package com.app.restaurant.controller.master;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.master.VariationRequestDTO;
import com.app.restaurant.dto.master.VariationResponseDTO;
import com.app.restaurant.response.ApiResponse;
import com.app.restaurant.service.master.VariationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master/variations")
@RequiredArgsConstructor
public class VariationController {

    private final VariationService variationService;

    @PostMapping
    public ResponseEntity<ApiResponse<VariationResponseDTO>> createVariation(
            @RequestBody VariationRequestDTO dto) {
        VariationResponseDTO created = variationService.createVariation(dto);

        return ResponseEntity.ok(ApiResponse.<VariationResponseDTO>builder()
                .status(true)
                .message(MessageConstants.CREATED_SUCCESSFULLY)
                .error(null)
                .data(created)
                .build());
    }

    @PutMapping("/{variationId}")
    public ResponseEntity<ApiResponse<VariationResponseDTO>> updateVariation(
            @PathVariable Integer variationId,
            @RequestBody VariationRequestDTO dto) {
        VariationResponseDTO updated = variationService.updateVariation(variationId, dto);

        return ResponseEntity.ok(ApiResponse.<VariationResponseDTO>builder()
                .status(true)
                .message(MessageConstants.UPDATED_SUCCESSFULLY)
                .error(null)
                .data(updated)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<VariationResponseDTO>>> getVariations(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "false") Boolean unpaged,
            @ParameterObject Pageable pageable) {

        Page<VariationResponseDTO> variations = variationService.getAllVariations(status, search, unpaged, pageable);

        return ResponseEntity.ok(ApiResponse.<Page<VariationResponseDTO>>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(variations)
                .build());
    }

    @GetMapping("/{variationId}")
    public ResponseEntity<ApiResponse<VariationResponseDTO>> getVariationById(@PathVariable Integer variationId) {
        VariationResponseDTO variation = variationService.getVariationById(variationId);

        return ResponseEntity.ok(ApiResponse.<VariationResponseDTO>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(variation)
                .build());
    }

    @PatchMapping("/{variationId}/toggle-status")
    public ResponseEntity<ApiResponse<String>> toggleVariationStatus(@PathVariable Integer variationId) {
        String msg = variationService.toggleVariationStatus(variationId);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(true)
                .message(MessageConstants.TOGGLED_SUCCESSFULLY)
                .error(null)
                .data(msg)
                .build());
    }
}
