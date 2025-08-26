package com.app.restaurant.dto.master;

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
public class RestaurantResponseDTO {
    private Integer id;
    private String name;
    private String cuisine;
    private String description;
    private String address;
    private String contact;
    private String logo;
    private String banner;
    private Boolean status;
}