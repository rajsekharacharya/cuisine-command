package com.app.restaurant.dto.admin;

import com.app.restaurant.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsersResponseDTO {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String mobile;
    private RoleType role;
    private boolean enabled;
    private boolean verified;
}