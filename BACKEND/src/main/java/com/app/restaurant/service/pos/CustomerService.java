package com.app.restaurant.service.pos;

import java.util.List;

import com.app.restaurant.dto.pos.CustomerDTO;

public interface CustomerService {

    /**
     * Creates a new customer.
     *
     * @param customerDTO the customer data transfer object containing customer
     *                    details
     * @return the created customer data transfer object
     */
    CustomerDTO createCustomer(CustomerDTO customerDTO);

    /**
     * Updates an existing customer.
     *
     * @param customerId  the ID of the customer to update
     * @param customerDTO the updated customer data transfer object
     * @return the updated customer data transfer object
     */
    CustomerDTO updateCustomer(Integer customerId, CustomerDTO customerDTO);

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return the retrieved customer data transfer object
     */
    CustomerDTO getCustomerById(Integer customerId);

    /**
     * Retrieves all customers.
     *
     * @return an iterable of customer data transfer objects
     */
    List<CustomerDTO> getAllCustomers();

}
