package com.allegromini.front.service;

import com.allegromini.front.dto.AccountDTO;
import com.allegromini.front.exception.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizationService {

    private RestTemplate restTemplate;

    public AuthorizationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void registerAccount(AccountDTO accountDTO) {
        if (!accountDTO.getRepeatPassword().equals(accountDTO.getPassword())) {
            throw new AuthorizationServiceException("The passwords need to be the same.");
        }
        if (!accountDTO.isTos()) {
            throw new AuthorizationServiceException("You need to accept the Terms Of Service.");
        }
        restTemplate.postForEntity("http://localhost:8080/api/v1/accounts", accountDTO, String.class);
    }

    /*public void loginAccount(LoginRequest loginRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(loginRequest.getEmail());
        if (optionalAccount.isEmpty() || !optionalAccount.get().getPassword().equals(loginRequest.getPassword())) {
            throw new AuthorizationServiceException("The email/password is incorrect.");
        }
        System.out.println("Logging in.");
    }*/
}
