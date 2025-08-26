package com.app.restaurant.serviceimpl.pos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.pos.CustomerDTO;
import com.app.restaurant.model.pos.Customer;
import com.app.restaurant.repository.pos.CustomerRepository;
import com.app.restaurant.service.pos.CustomerService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        if (customerDTO.getName() == null || customerDTO.getName().isEmpty()) {
            throw new IllegalArgumentException(MessageConstants.MISSING_REQUIRED_FIELDS);
        }
        if (customerDTO.getPhone() == null || customerDTO.getPhone().isEmpty()) {
            throw new IllegalArgumentException(MessageConstants.MISSING_REQUIRED_FIELDS);
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer = customerRepository.save(customer);

        return mapToDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));

        if (customerDTO.getName() != null && !customerDTO.getName().isEmpty()) {
            customer.setName(customerDTO.getName());
        }
        if (customerDTO.getPhone() != null && !customerDTO.getPhone().isEmpty()) {
            customer.setPhone(customerDTO.getPhone());
        }
        customer.setEmail(customerDTO.getEmail());
        customer = customerRepository.save(customer);

        return mapToDTO(customer);
    }

    @Override
    public CustomerDTO getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));
        return mapToDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Map Customer to CustomerDTO
    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        return dto;
    }

}
