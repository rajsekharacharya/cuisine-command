package com.app.restaurant.dto.pos;

import com.app.restaurant.enums.Dietary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PosResponseDTO {

    private List<CategoryDTO> categories = new ArrayList<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class CategoryDTO {
        private Integer id;
        private String name;
        private String image;
        private List<ItemDTO> items = new ArrayList<>();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ItemDTO {
        private Integer id;
        private String name;
        private BigDecimal price;
        private Boolean variation;
        private Dietary dietary;
        private Boolean addons;
        private Boolean combo;
        private List<VariationDTO> variations = new ArrayList<>();
        private List<ItemAddonDTO> itemAddons = new ArrayList<>();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class VariationDTO {
        private Integer id;
        private String name;
        private BigDecimal price;
        private Boolean addons = false;
        private List<ItemAddonDTO> itemAddons = new ArrayList<>();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ItemAddonDTO {
        private Integer id;
        private String name;
        private BigDecimal price;
    }
}