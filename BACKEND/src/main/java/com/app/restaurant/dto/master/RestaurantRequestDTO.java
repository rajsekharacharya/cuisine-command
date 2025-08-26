package com.app.restaurant.dto.master;

import jakarta.validation.constraints.NotBlank;
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
public class RestaurantRequestDTO {
    @NotBlank
    private String name;
    private String cuisine;
    private String description;
    private String address;
    private String contact;
    private Boolean status = true;
}