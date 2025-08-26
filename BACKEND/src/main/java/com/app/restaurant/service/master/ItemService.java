package com.app.restaurant.service.master;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.restaurant.dto.master.ItemRequestDTO;
import com.app.restaurant.dto.master.ItemResponseDTO;
import com.app.restaurant.dto.pos.PosResponseDTO;

public interface ItemService {
    ItemResponseDTO createItem(ItemRequestDTO dto);

    ItemResponseDTO updateItem(Integer itemId, ItemRequestDTO dto);

    Page<ItemResponseDTO> getAllItems(Boolean status, String search, Boolean combo, Boolean unpaged, Pageable pageable);

    ItemResponseDTO getItemById(Integer itemId);

    String toggleItemStatus(Integer itemId);

    PosResponseDTO getPosData();
}
