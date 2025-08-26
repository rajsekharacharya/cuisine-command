package com.app.restaurant.serviceimpl.master;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.master.ItemRequestDTO;
import com.app.restaurant.dto.master.ItemResponseDTO;
import com.app.restaurant.dto.pos.PosResponseDTO;
import com.app.restaurant.exception.ResourceNotFoundException;
import com.app.restaurant.model.master.Category;
import com.app.restaurant.model.master.Item;
import com.app.restaurant.model.master.ItemAddon;
import com.app.restaurant.model.master.ItemVariation;
import com.app.restaurant.repository.master.AddonsRepository;
import com.app.restaurant.repository.master.CategoryRepository;
import com.app.restaurant.repository.master.ItemRepository;
import com.app.restaurant.repository.master.VariationRepository;
import com.app.restaurant.service.master.ItemService;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final AddonsRepository addonsRepository;
    private final VariationRepository variationRepository;

    @Override
    @Transactional
    public ItemResponseDTO createItem(ItemRequestDTO dto) {
        log.info("Creating a new item with name: {}", dto.getName());
        Item item = new Item();
        populateItemFromDTO(item, dto);
        Item savedItem = itemRepository.save(item);
        log.info(MessageConstants.CREATED_SUCCESSFULLY);
        return toResponseDTO(savedItem);
    }

    @Override
    @Transactional
    public ItemResponseDTO updateItem(Integer itemId, ItemRequestDTO dto) {
        log.info("Updating item with ID: {}", itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        populateItemFromDTO(item, dto);
        Item savedItem = itemRepository.save(item);
        log.info(MessageConstants.UPDATED_SUCCESSFULLY);
        return toResponseDTO(savedItem);
    }

    @Override
    public Page<ItemResponseDTO> getAllItems(Boolean status, String search, Boolean combo, Boolean unpaged,
            Pageable pageable) {
        log.info("Fetching all items with status: {}, combo: {}, search: {}", status, combo, search);
        Specification<Item> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (combo != null) {
                predicate = cb.and(predicate, cb.equal(root.get("combo"), combo));
            }
            if (search != null && !search.trim().isEmpty()) {
                Predicate namePredicate = cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%");
                Join<Item, Category> categoryJoin = root.join("category", JoinType.LEFT);
                Predicate categoryPredicate = cb.like(cb.lower(categoryJoin.get("name")),
                        "%" + search.toLowerCase() + "%");
                predicate = cb.and(predicate, cb.or(namePredicate, categoryPredicate));
            }
            return predicate;
        };

        if (Boolean.TRUE.equals(unpaged)) {
            List<Item> all = itemRepository.findAll(spec);
            List<ItemResponseDTO> dtos = all.stream().map(this::toResponseDTO).toList();
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return new PageImpl<>(dtos);
        } else {
            Page<Item> page = itemRepository.findAll(spec, pageable);
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return page.map(this::toResponseDTO);
        }
    }

    @Override
    public ItemResponseDTO getItemById(Integer itemId) {
        log.info("Fetching item with ID: {}", itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        log.info(MessageConstants.FETCHED_SUCCESSFULLY);
        return toResponseDTO(item);
    }

    @Override
    @Transactional
    public String toggleItemStatus(Integer itemId) {
        log.info("Toggling status for item with ID: {}", itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));

        item.setStatus(!item.isStatus());
        itemRepository.save(item);

        String message = item.isStatus()
                ? "Item activated successfully."
                : "Item deactivated successfully.";
        log.info(message);
        return message;
    }

    @Override
    public PosResponseDTO getPosData() {
        log.info("Fetching POS data with active categories, items, and variations");
        Specification<Category> categorySpec = (root, query, cb) -> cb.equal(root.get("status"), true);

        List<Category> categories = categoryRepository.findAll(categorySpec);
        List<PosResponseDTO.CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> {
                    PosResponseDTO.CategoryDTO categoryDTO = new PosResponseDTO.CategoryDTO();
                    categoryDTO.setId(category.getId());
                    categoryDTO.setName(category.getName());
                    categoryDTO.setImage(category.getImage());

                    Specification<Item> itemSpec = (root, query, cb) -> {
                        Predicate predicate = cb.conjunction();
                        predicate = cb.and(predicate, cb.equal(root.get("status"), true));
                        predicate = cb.and(predicate, cb.equal(root.get("category"), category));
                        return predicate;
                    };

                    List<Item> items = itemRepository.findAll(itemSpec);
                    List<PosResponseDTO.ItemDTO> itemDTOs = items.stream()
                            .map(item -> {
                                PosResponseDTO.ItemDTO itemDTO = new PosResponseDTO.ItemDTO();
                                itemDTO.setId(item.getId());
                                itemDTO.setName(item.getName());
                                itemDTO.setDietary(item.getDietary());
                                itemDTO.setPrice(item.getPrice());
                                itemDTO.setVariation(item.isVariation());
                                itemDTO.setAddons(item.isAddons());
                                itemDTO.setCombo(item.isCombo());

                                // Map variations
                                List<PosResponseDTO.VariationDTO> variationDTOs = item.getItemVariations().stream()
                                        .filter(ItemVariation::isStatus)
                                        .map(variation -> {
                                            PosResponseDTO.VariationDTO variationDTO = new PosResponseDTO.VariationDTO();
                                            variationDTO.setId(variation.getId());
                                            variationDTO.setName(variation.getName());
                                            variationDTO.setPrice(variation.getPrice());
                                            variationDTO.setAddons(variation.isAddons());
                                            // Map add-ons for variant
                                            List<PosResponseDTO.ItemAddonDTO> addonDTOs = variation.getItemAddons()
                                                    .stream()
                                                    .filter(ItemAddon::isStatus)
                                                    .map(addon -> new PosResponseDTO.ItemAddonDTO(
                                                            addon.getId(),
                                                            addon.getName(),
                                                            addon.getPrice()))
                                                    .collect(Collectors.toList());
                                            variationDTO.setItemAddons(addonDTOs);
                                            return variationDTO;
                                        })
                                        .collect(Collectors.toList());
                                itemDTO.setVariations(variationDTOs);

                                // Map add-ons for item (non-variant)
                                List<PosResponseDTO.ItemAddonDTO> itemAddonDTOs = item.getItemAddons().stream()
                                        .filter(ItemAddon::isStatus)
                                        .map(addon -> new PosResponseDTO.ItemAddonDTO(
                                                addon.getId(),
                                                addon.getName(),
                                                addon.getPrice()))
                                        .collect(Collectors.toList());
                                itemDTO.setItemAddons(itemAddonDTOs);

                                return itemDTO;
                            })
                            .collect(Collectors.toList());
                    categoryDTO.setItems(itemDTOs);
                    return categoryDTO;
                })
                .collect(Collectors.toList());

        PosResponseDTO posResponseDTO = new PosResponseDTO();
        posResponseDTO.setCategories(categoryDTOs);
        log.info(MessageConstants.FETCHED_SUCCESSFULLY);
        return posResponseDTO;
    }

    private void populateItemFromDTO(Item item, ItemRequestDTO dto) {
        log.info("Populating item from DTO: {}", dto);
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + dto.getCategoryId()));
        item.setCategory(category);
        item.setName(dto.getName());
        item.setShortCode(dto.getShortCode());
        item.setPrice(dto.getVariation() != null && dto.getVariation() ? null : dto.getPrice());
        item.setDescription(dto.getDescription());
        item.setDietary(dto.getDietary());
        item.setGstType(dto.getGstType());
        item.setOrderTypes(dto.getOrderTypes() != null ? dto.getOrderTypes() : new ArrayList<>());
        item.setVariation(dto.getVariation() != null ? dto.getVariation() : false);
        item.setAddons(dto.getAddons() != null ? dto.getAddons() : false);
        item.setStatus(dto.getStatus() != null ? dto.getStatus() : true);
        item.setCombo(dto.getCombo() != null ? dto.getCombo() : false);


        // Update variations
        if (dto.getVariations() != null && !dto.getVariations().isEmpty()) {
            // Validate variation names against Variation master
            List<String> validVariationNames = variationRepository.findAllByStatusTrue()
                    .stream()
                    .map(variation -> variation.getName())
                    .collect(Collectors.toList());

            for (ItemRequestDTO.VariationDTO v : dto.getVariations()) {
                if (!validVariationNames.contains(v.getName())) {
                    throw new IllegalArgumentException("Invalid variation name: " + v.getName());
                }

                ItemVariation variation;
                if (v.getId() != null) {
                    // Update existing variation
                    variation = item.getItemVariations().stream()
                            .filter(existing -> existing.getId().equals(v.getId()))
                            .findFirst()
                            .orElse(new ItemVariation());
                } else {
                    // Create new variation
                    variation = new ItemVariation();
                }

                variation.setItem(item);
                variation.setName(v.getName());
                variation.setPrice(v.getPrice());
                variation.setStatus(v.getStatus() != null ? v.getStatus() : true);
                variation.setAddons(v.getAddons() != null ? v.getAddons() : false);

                // Update add-ons for variant
                if (v.getItemAddons() != null && !v.getItemAddons().isEmpty()) {
                    // Validate add-on names against Addons master
                    List<String> validAddonNames = addonsRepository.findAllByStatusTrue()
                            .stream()
                            .map(addon -> addon.getName())
                            .collect(Collectors.toList());

                    for (ItemRequestDTO.ItemAddonDTO a : v.getItemAddons()) {
                        if (!validAddonNames.contains(a.getName())) {
                            throw new IllegalArgumentException("Invalid add-on name: " + a.getName());
                        }
                        ItemAddon addon;

                        if (a.getId() != null) {
                            // Update existing addon
                            addon = variation.getItemAddons().stream()
                                    .filter(existing -> existing.getId().equals(a.getId()))
                                    .findFirst()
                                    .orElse(new ItemAddon());
                        } else {
                            // Create new addon
                            addon = new ItemAddon();
                        }
                        addon.setName(a.getName());
                        addon.setPrice(a.getPrice());
                        addon.setStatus(a.getStatus() != null ? a.getStatus() : true);
                        addon.setItemVariation(variation);
                        variation.getItemAddons().add(addon);
                    }
                }
                item.getItemVariations().add(variation);
            }
        }

        // Update add-ons for item (non-variant)
        if (dto.getItemAddons() != null && !dto.getItemAddons().isEmpty()) {
            // Validate add-on names against Addons master
            List<String> validAddonNames = addonsRepository.findAllByStatusTrue()
                    .stream()
                    .map(addon -> addon.getName())
                    .collect(Collectors.toList());

            for (ItemRequestDTO.ItemAddonDTO a : dto.getItemAddons()) {
                if (!validAddonNames.contains(a.getName())) {
                    throw new IllegalArgumentException("Invalid add-on name: " + a.getName());
                }
                ItemAddon addon;
                if (a.getId() != null) {
                    // Update existing addon
                    addon = item.getItemAddons().stream()
                            .filter(existing -> existing.getId().equals(a.getId()))
                            .findFirst()
                            .orElse(new ItemAddon());
                } else {
                    // Create new addon
                    addon = new ItemAddon();
                }
                addon.setName(a.getName());
                addon.setPrice(a.getPrice());
                addon.setStatus(a.getStatus() != null ? a.getStatus() : true);
                addon.setItem(item);
                item.getItemAddons().add(addon);
            }
        }
    }

    private ItemResponseDTO toResponseDTO(Item item) {
        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setId(item.getId());
        dto.setCategoryId(item.getCategory().getId());
        dto.setCategoryName(item.getCategory().getName());
        dto.setName(item.getName());
        dto.setShortCode(item.getShortCode());
        dto.setPrice(item.getPrice());
        dto.setDescription(item.getDescription());
        dto.setDietary(item.getDietary());
        dto.setGstType(item.getGstType());
        dto.setOrderTypes(item.getOrderTypes());
        dto.setVariation(item.isVariation());
        dto.setAddons(item.isAddons());
        dto.setCombo(item.isCombo());
        dto.setStatus(item.isStatus());

        // Map variations
        List<ItemResponseDTO.VariationDTO> variations = item.getItemVariations().stream()
                .map(v -> {
                    ItemResponseDTO.VariationDTO vDto = new ItemResponseDTO.VariationDTO();
                    vDto.setId(v.getId());
                    vDto.setName(v.getName());
                    vDto.setPrice(v.getPrice());
                    vDto.setStatus(v.isStatus());
                    vDto.setAddons(v.isAddons());
                    // Map add-ons for variant
                    List<ItemResponseDTO.ItemAddonDTO> addonDtos = v.getItemAddons().stream()
                            .map(a -> new ItemResponseDTO.ItemAddonDTO(a.getId(), a.getName(), a.getPrice(),
                                    a.isStatus()))
                            .collect(Collectors.toList());
                    vDto.setItemAddons(addonDtos);
                    return vDto;
                })
                .collect(Collectors.toList());
        dto.setVariations(variations);

        // Map add-ons for item (non-variant)
        List<ItemResponseDTO.ItemAddonDTO> itemAddonDtos = item.getItemAddons().stream()
                .map(a -> new ItemResponseDTO.ItemAddonDTO(a.getId(), a.getName(), a.getPrice(), a.isStatus()))
                .collect(Collectors.toList());
        dto.setItemAddons(itemAddonDtos);
        return dto;
    }
}