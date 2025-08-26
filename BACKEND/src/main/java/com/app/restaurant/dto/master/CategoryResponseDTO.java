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
public class CategoryResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private String image;
    private Boolean status;
}