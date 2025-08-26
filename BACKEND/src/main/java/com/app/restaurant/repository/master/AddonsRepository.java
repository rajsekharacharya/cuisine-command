package com.app.restaurant.repository.master;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.app.restaurant.model.master.Addons;

public interface AddonsRepository extends JpaRepository<Addons, Integer>, JpaSpecificationExecutor<Addons> {

    List<Addons> findAllByStatusTrue();

    Optional<Addons> findByNameAndStatusTrue(String name);
}