package com.app.restaurant.service.master;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.restaurant.dto.master.AddonsRequestDTO;
import com.app.restaurant.dto.master.AddonsResponseDTO;

public interface AddonsService {
    AddonsResponseDTO createAddons(AddonsRequestDTO dto);

    AddonsResponseDTO updateAddons(Integer addonsId, AddonsRequestDTO dto);

    Page<AddonsResponseDTO> getAllAddons(Boolean status, String search, Boolean unpaged, Pageable pageable);

    AddonsResponseDTO getAddonsById(Integer addonsId);

    String toggleAddonsStatus(Integer addonsId);
}
