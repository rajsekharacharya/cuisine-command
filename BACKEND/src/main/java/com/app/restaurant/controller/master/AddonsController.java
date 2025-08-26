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
import com.app.restaurant.dto.master.AddonsRequestDTO;
import com.app.restaurant.dto.master.AddonsResponseDTO;
import com.app.restaurant.response.ApiResponse;
import com.app.restaurant.service.master.AddonsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master/addons")
@RequiredArgsConstructor
public class AddonsController {

    private final AddonsService addonsService;

    @PostMapping
    public ResponseEntity<ApiResponse<AddonsResponseDTO>> createAddons(
            @RequestBody AddonsRequestDTO dto) {
        AddonsResponseDTO created = addonsService.createAddons(dto);

        return ResponseEntity.ok(ApiResponse.<AddonsResponseDTO>builder()
                .status(true)
                .message(MessageConstants.CREATED_SUCCESSFULLY)
                .error(null)
                .data(created)
                .build());
    }

    @PutMapping("/{addonsId}")
    public ResponseEntity<ApiResponse<AddonsResponseDTO>> updateAddons(
            @PathVariable Integer addonsId,
            @RequestBody AddonsRequestDTO dto) {
        AddonsResponseDTO updated = addonsService.updateAddons(addonsId, dto);

        return ResponseEntity.ok(ApiResponse.<AddonsResponseDTO>builder()
                .status(true)
                .message(MessageConstants.UPDATED_SUCCESSFULLY)
                .error(null)
                .data(updated)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AddonsResponseDTO>>> getAddons(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "false") Boolean unpaged,
            @ParameterObject Pageable pageable) {

        Page<AddonsResponseDTO> addons = addonsService.getAllAddons(status, search, unpaged, pageable);

        return ResponseEntity.ok(ApiResponse.<Page<AddonsResponseDTO>>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(addons)
                .build());
    }

    @GetMapping("/{addonsId}")
    public ResponseEntity<ApiResponse<AddonsResponseDTO>> getAddonsById(@PathVariable Integer addonsId) {
        AddonsResponseDTO addons = addonsService.getAddonsById(addonsId);

        return ResponseEntity.ok(ApiResponse.<AddonsResponseDTO>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(addons)
                .build());
    }

    @PatchMapping("/{addonsId}/toggle-status")
    public ResponseEntity<ApiResponse<String>> toggleAddonsStatus(@PathVariable Integer addonsId) {
        String msg = addonsService.toggleAddonsStatus(addonsId);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(true)
                .message(MessageConstants.TOGGLED_SUCCESSFULLY)
                .error(null)
                .data(msg)
                .build());
    }
}