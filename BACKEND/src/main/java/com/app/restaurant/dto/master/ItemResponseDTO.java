package com.app.restaurant.dto.master;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.app.restaurant.enums.Dietary;
import com.app.restaurant.enums.GstType;
import com.app.restaurant.enums.OrderType;

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
public class ItemResponseDTO {

    private Integer id;

    private Integer categoryId;

    private String categoryName;

    private String name;

    private String shortCode;

    private BigDecimal price;

    private String description;

    private Dietary dietary;

    private GstType gstType;

    private List<OrderType> orderTypes = new ArrayList<>();

    private Boolean variation;

    private Boolean addons;

    private Boolean combo;

    private Boolean status;

    private List<VariationDTO> variations = new ArrayList<>();

    private List<ItemAddonDTO> itemAddons = new ArrayList<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class VariationDTO {
        private Integer id;
        private String name; // From Variation master
        private BigDecimal price;
        private Boolean status;
        private Boolean addons;
        private List<ItemAddonDTO> itemAddons = new ArrayList<>();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ItemAddonDTO {
        private Integer id;
        private String name; // From Addons master
        private BigDecimal price;
        private Boolean status;
    }
}