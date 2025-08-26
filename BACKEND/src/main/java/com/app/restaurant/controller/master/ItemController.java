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
import com.app.restaurant.dto.master.ItemRequestDTO;
import com.app.restaurant.dto.master.ItemResponseDTO;
import com.app.restaurant.dto.pos.PosResponseDTO;
import com.app.restaurant.response.ApiResponse;
import com.app.restaurant.service.master.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponseDTO>> createItem(@RequestBody ItemRequestDTO dto) {
        ItemResponseDTO created = itemService.createItem(dto);
        return ResponseEntity.ok(ApiResponse.<ItemResponseDTO>builder()
                .status(true)
                .message(MessageConstants.CREATED_SUCCESSFULLY)
                .error(null)
                .data(created)
                .build());
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponseDTO>> updateItem(
            @PathVariable Integer itemId,
            @RequestBody ItemRequestDTO dto) {
        ItemResponseDTO updated = itemService.updateItem(itemId, dto);
        return ResponseEntity.ok(ApiResponse.<ItemResponseDTO>builder()
                .status(true)
                .message(MessageConstants.UPDATED_SUCCESSFULLY)
                .error(null)
                .data(updated)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ItemResponseDTO>>> getItems(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Boolean combo,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "false") Boolean unpaged,
            @ParameterObject Pageable pageable) {
        Page<ItemResponseDTO> items = itemService.getAllItems(status, search, combo, unpaged, pageable);
        return ResponseEntity.ok(ApiResponse.<Page<ItemResponseDTO>>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(items)
                .build());
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponseDTO>> getItemById(@PathVariable Integer itemId) {
        ItemResponseDTO item = itemService.getItemById(itemId);
        return ResponseEntity.ok(ApiResponse.<ItemResponseDTO>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(item)
                .build());
    }

    @PatchMapping("/{itemId}/toggle-status")
    public ResponseEntity<ApiResponse<String>> toggleItemStatus(@PathVariable Integer itemId) {
        String msg = itemService.toggleItemStatus(itemId);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(true)
                .message(MessageConstants.TOGGLED_SUCCESSFULLY)
                .error(null)
                .data(msg)
                .build());
    }

    @GetMapping("/pos")
    public ResponseEntity<ApiResponse<PosResponseDTO>> getPosData() {
        PosResponseDTO posData = itemService.getPosData();
        return ResponseEntity.ok(ApiResponse.<PosResponseDTO>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(posData)
                .build());
    }
}