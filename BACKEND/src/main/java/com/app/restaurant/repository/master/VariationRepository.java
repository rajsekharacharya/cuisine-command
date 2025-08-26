package com.app.restaurant.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.app.restaurant.model.master.Variation;

public interface VariationRepository extends JpaRepository<Variation, Integer>, JpaSpecificationExecutor<Variation> {

    List<Variation> findAllByStatusTrue();
}
