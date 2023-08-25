package com.allegromini.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {
    @GetMapping("/add-customer")
    public String addCustomer() {
        return "addcustomer.html";
    }

    @GetMapping("/get-customers")
    public String getCustomers() {
        return "getcustomers.html";
    }
}
