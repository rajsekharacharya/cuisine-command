package com.app.restaurant.controller.pos;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.pos.CustomerDTO;
import com.app.restaurant.response.ApiResponse;
import com.app.restaurant.service.pos.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pos/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDTO>> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(ApiResponse.<CustomerDTO>builder()
                .status(true)
                .message(MessageConstants.CREATED_SUCCESSFULLY)
                .error(null)
                .data(createdCustomer)
                .build());
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(
            @PathVariable Integer customerId,
            @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(customerId, customerDTO);
        return ResponseEntity.ok(ApiResponse.<CustomerDTO>builder()
                .status(true)
                .message(MessageConstants.UPDATED_SUCCESSFULLY)
                .error(null)
                .data(updatedCustomer)
                .build());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomerById(@PathVariable Integer customerId) {
        CustomerDTO customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(ApiResponse.<CustomerDTO>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(customer)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(ApiResponse.<List<CustomerDTO>>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(customers)
                .build());
    }

}
