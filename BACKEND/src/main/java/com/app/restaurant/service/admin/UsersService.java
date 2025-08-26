package com.app.restaurant.service.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.restaurant.dto.admin.UsersRequestDTO;
import com.app.restaurant.dto.admin.UsersResponseDTO;

public interface UsersService {
    UsersResponseDTO createUser(UsersRequestDTO dto);

    UsersResponseDTO updateUser(Integer userId, UsersRequestDTO dto);

    Page<UsersResponseDTO> getAllUsers(Boolean enabled, String search, String searchField, Boolean unpaged,
            Pageable pageable);

    UsersResponseDTO getUserById(Integer userId);

    String toggleUserStatus(Integer userId);

    void verifyEmail(String token);
}