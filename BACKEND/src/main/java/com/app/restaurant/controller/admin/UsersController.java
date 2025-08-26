package com.app.restaurant.controller.admin;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.restaurant.dto.admin.UsersRequestDTO;
import com.app.restaurant.dto.admin.UsersResponseDTO;
import com.app.restaurant.response.ApiResponse;
import com.app.restaurant.security.MyUserDetail;
import com.app.restaurant.service.admin.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UsersController {

        private final UsersService usersService;

        @PostMapping
        public ResponseEntity<ApiResponse<UsersResponseDTO>> createUser(
                        @AuthenticationPrincipal MyUserDetail userDetails,
                        @RequestBody UsersRequestDTO dto) {

                UsersResponseDTO created = usersService.createUser(dto);

                return ResponseEntity.ok(ApiResponse.<UsersResponseDTO>builder()
                                .status(true)
                                .message("User created successfully. Verification email sent.")
                                .error(null)
                                .data(created)
                                .build());
        }

        @PutMapping("/{userId}")
        public ResponseEntity<ApiResponse<UsersResponseDTO>> updateUser(
                        @AuthenticationPrincipal MyUserDetail userDetails,
                        @PathVariable Integer userId,
                        @RequestBody UsersRequestDTO dto) {

                UsersResponseDTO updated = usersService.updateUser(userId, dto);

                return ResponseEntity.ok(ApiResponse.<UsersResponseDTO>builder()
                                .status(true)
                                .message("User updated successfully.")
                                .error(null)
                                .data(updated)
                                .build());
        }

        @GetMapping
        public ResponseEntity<ApiResponse<Page<UsersResponseDTO>>> getUsers(
                        @RequestParam(required = false) Boolean enabled,
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String searchField,
                        @RequestParam(required = false, defaultValue = "false") Boolean unpaged,
                        @ParameterObject Pageable pageable) {

                Page<UsersResponseDTO> users = usersService.getAllUsers(enabled, search, searchField, unpaged,
                                pageable);

                return ResponseEntity.ok(ApiResponse.<Page<UsersResponseDTO>>builder()
                                .status(true)
                                .message("Users fetched successfully.")
                                .error(null)
                                .data(users)
                                .build());
        }

        @GetMapping("/{userId}")
        public ResponseEntity<ApiResponse<UsersResponseDTO>> getUserById(@PathVariable Integer userId) {
                UsersResponseDTO user = usersService.getUserById(userId);

                return ResponseEntity.ok(ApiResponse.<UsersResponseDTO>builder()
                                .status(true)
                                .message("User fetched successfully.")
                                .error(null)
                                .data(user)
                                .build());
        }

        @PatchMapping("/{userId}/toggle-status")
        public ResponseEntity<ApiResponse<String>> toggleUserStatus(
                        @AuthenticationPrincipal MyUserDetail userDetails,
                        @PathVariable Integer userId) {

                String msg = usersService.toggleUserStatus(userId);

                return ResponseEntity.ok(ApiResponse.<String>builder()
                                .status(true)
                                .message("User status toggled successfully.")
                                .error(null)
                                .data(msg)
                                .build());
        }

        @GetMapping("/verify")
        public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam String token) {
                usersService.verifyEmail(token);

                return ResponseEntity.ok(ApiResponse.<String>builder()
                                .status(true)
                                .message("Email verified successfully.")
                                .error(null)
                                .data("Email verified")
                                .build());
        }
}
