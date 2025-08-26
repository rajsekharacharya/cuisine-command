package com.app.restaurant.serviceimpl.master;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.master.VariationRequestDTO;
import com.app.restaurant.dto.master.VariationResponseDTO;
import com.app.restaurant.exception.ResourceNotFoundException;
import com.app.restaurant.model.master.Variation;
import com.app.restaurant.repository.master.VariationRepository;
import com.app.restaurant.service.master.VariationService;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VariationServiceImpl implements VariationService {

    private final VariationRepository variationRepository;

    @Override
    @Transactional
    public VariationResponseDTO createVariation(VariationRequestDTO dto) {
        log.info("Creating a new variation with name: {}", dto.getName());
        Variation variation = new Variation();
        variation.setName(dto.getName());
        variation.setDescription(dto.getDescription());
        variation.setStatus(dto.getStatus());

        Variation savedVariation = variationRepository.save(variation);
        log.info(MessageConstants.CREATED_SUCCESSFULLY);
        return toResponseDTO(savedVariation);
    }

    @Override
    @Transactional
    public VariationResponseDTO updateVariation(Integer variationId, VariationRequestDTO dto) {
        log.info("Updating variation with ID: {}", variationId);
        Variation variation = variationRepository.findById(variationId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));

        variation.setName(dto.getName());
        variation.setDescription(dto.getDescription());
        variation.setStatus(dto.getStatus() != null ? dto.getStatus() : variation.isStatus());

        Variation savedVariation = variationRepository.save(variation);
        log.info(MessageConstants.UPDATED_SUCCESSFULLY);
        return toResponseDTO(savedVariation);
    }

    @Override
    public Page<VariationResponseDTO> getAllVariations(Boolean status, String search, Boolean unpaged, Pageable pageable) {
        log.info("Fetching all variations with status: {}, search: {}", status, search);
        Specification<Variation> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (search != null && !search.trim().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"));
            }
            return predicate;
        };

        if (Boolean.TRUE.equals(unpaged)) {
            List<Variation> all = variationRepository.findAll(spec);
            List<VariationResponseDTO> dtos = all.stream().map(this::toResponseDTO).toList();
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return new PageImpl<>(dtos);
        } else {
            Page<Variation> page = variationRepository.findAll(spec, pageable);
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return page.map(this::toResponseDTO);
        }
    }

    @Override
    public VariationResponseDTO getVariationById(Integer variationId) {
        log.info("Fetching variation with ID: {}", variationId);
        Variation variation = variationRepository.findById(variationId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        log.info(MessageConstants.FETCHED_SUCCESSFULLY);
        return toResponseDTO(variation);
    }

    @Override
    @Transactional
    public String toggleVariationStatus(Integer variationId) {
        log.info("Toggling status for variation with ID: {}", variationId);
        Variation variation = variationRepository.findById(variationId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));

        variation.setStatus(!variation.isStatus());
        variationRepository.save(variation);

        String message = variation.isStatus()
                ? "Variation activated successfully."
                : "Variation deactivated successfully.";
        log.info(message);
        return message;
    }

    private VariationResponseDTO toResponseDTO(Variation variation) {
        VariationResponseDTO dto = new VariationResponseDTO();
        dto.setId(variation.getId());
        dto.setName(variation.getName());
        dto.setDescription(variation.getDescription());
        dto.setStatus(variation.isStatus());
        return dto;
    }
}