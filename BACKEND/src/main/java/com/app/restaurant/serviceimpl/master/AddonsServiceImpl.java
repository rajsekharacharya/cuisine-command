package com.app.restaurant.serviceimpl.master;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.master.AddonsRequestDTO;
import com.app.restaurant.dto.master.AddonsResponseDTO;
import com.app.restaurant.exception.ResourceNotFoundException;
import com.app.restaurant.model.master.Addons;
import com.app.restaurant.repository.master.AddonsRepository;
import com.app.restaurant.service.master.AddonsService;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AddonsServiceImpl implements AddonsService {

    private final AddonsRepository addonsRepository;

    @Override
    @Transactional
    public AddonsResponseDTO createAddons(AddonsRequestDTO dto) {
        log.info("Creating a new addons with name: {}", dto.getName());
        Addons addons = new Addons();
        addons.setName(dto.getName());
        addons.setDescription(dto.getDescription());
        addons.setStatus(dto.getStatus());

        Addons savedAddons = addonsRepository.save(addons);
        log.info(MessageConstants.CREATED_SUCCESSFULLY);
        return toResponseDTO(savedAddons);
    }

    @Override
    @Transactional
    public AddonsResponseDTO updateAddons(Integer addonsId, AddonsRequestDTO dto) {
        log.info("Updating addons with ID: {}", addonsId);
        Addons addons = addonsRepository.findById(addonsId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));

        addons.setName(dto.getName());
        addons.setDescription(dto.getDescription());
        addons.setStatus(dto.getStatus() != null ? dto.getStatus() : addons.isStatus());

        Addons savedAddons = addonsRepository.save(addons);
        log.info(MessageConstants.UPDATED_SUCCESSFULLY);
        return toResponseDTO(savedAddons);
    }

    @Override
    public Page<AddonsResponseDTO> getAllAddons(Boolean status, String search, Boolean unpaged, Pageable pageable) {
        log.info("Fetching all addons with status: {}, search: {}", status, search);
        Specification<Addons> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (search != null && !search.trim().isEmpty()) {
                String searchLower = "%" + search.toLowerCase() + "%";
                Predicate namePredicate = cb.like(cb.lower(root.get("name")), searchLower);
                Predicate descPredicate = cb.like(cb.lower(root.get("description")), searchLower);

                // Combine both using OR
                Predicate searchPredicate = cb.or(namePredicate, descPredicate);

                // Combine with main predicate using AND
                predicate = cb.and(predicate, searchPredicate);
            }
            return predicate;
        };

        if (Boolean.TRUE.equals(unpaged)) {
            List<Addons> all = addonsRepository.findAll(spec);
            List<AddonsResponseDTO> dtos = all.stream().map(this::toResponseDTO).toList();
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return new PageImpl<>(dtos);
        } else {
            Page<Addons> page = addonsRepository.findAll(spec, pageable);
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return page.map(this::toResponseDTO);
        }
    }

    @Override
    public AddonsResponseDTO getAddonsById(Integer addonsId) {
        log.info("Fetching addons with ID: {}", addonsId);
        Addons addons = addonsRepository.findById(addonsId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        log.info(MessageConstants.FETCHED_SUCCESSFULLY);
        return toResponseDTO(addons);
    }

    @Override
    @Transactional
    public String toggleAddonsStatus(Integer addonsId) {
        log.info("Toggling status for addons with ID: {}", addonsId);
        Addons addons = addonsRepository.findById(addonsId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));

        addons.setStatus(!addons.isStatus());
        addonsRepository.save(addons);

        String message = addons.isStatus()
                ? "Addons activated successfully."
                : "Addons deactivated successfully.";
        log.info(message);
        return message;
    }

    private AddonsResponseDTO toResponseDTO(Addons addons) {
        AddonsResponseDTO dto = new AddonsResponseDTO();
        dto.setId(addons.getId());
        dto.setName(addons.getName());
        dto.setDescription(addons.getDescription());
        dto.setStatus(addons.isStatus());
        return dto;
    }
}