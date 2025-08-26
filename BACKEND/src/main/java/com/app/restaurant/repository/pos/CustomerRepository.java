package com.app.restaurant.repository.pos;


import org.springframework.data.jpa.repository.JpaRepository;

import com.app.restaurant.model.pos.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}