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
public class AddonsResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private Boolean status;
}
