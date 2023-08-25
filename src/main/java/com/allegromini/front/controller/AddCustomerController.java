package com.allegromini.front.controller;

import com.allegromini.front.dto.CustomerDTO;
import com.allegromini.front.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddCustomerController {
    CustomerService customerService;

    AddCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/addcustomer")
    public String postAddCustomer(String firstName, String lastName) {
        customerService.addNewCustomer(new CustomerDTO(firstName, lastName));
        return "home";
    }
}
