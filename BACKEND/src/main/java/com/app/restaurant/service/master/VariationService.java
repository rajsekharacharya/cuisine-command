package com.app.restaurant.service.master;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.restaurant.dto.master.VariationRequestDTO;
import com.app.restaurant.dto.master.VariationResponseDTO;

public interface VariationService {
    VariationResponseDTO createVariation(VariationRequestDTO dto);

    VariationResponseDTO updateVariation(Integer variationId, VariationRequestDTO dto);

    Page<VariationResponseDTO> getAllVariations(Boolean status, String search, Boolean unpaged, Pageable pageable);

    VariationResponseDTO getVariationById(Integer variationId);

    String toggleVariationStatus(Integer variationId);
}
