package com.allegromini.front.service;

import com.allegromini.front.dto.AccountDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService {
    private RestTemplate restTemplate;

    AccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void addNewAccount(AccountDTO newAccountDTO) {
        restTemplate.postForEntity("http://localhost:8080/api/v1/accounts", newAccountDTO, String.class);
    }
}
