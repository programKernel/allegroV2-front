package com.allegromini.front.service;

import com.allegromini.front.dto.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
    private RestTemplate restTemplate;

    CustomerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void addNewCustomer(CustomerDTO newCustomerDTO) {
        restTemplate.postForEntity("http://localhost:8080/api/v1/customers", newCustomerDTO, String.class);
    }
}
