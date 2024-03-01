package com.allegromini.front.service;

import com.allegromini.front.dto.AccountDTO;
import com.allegromini.front.exception.AuthorizationServiceException;
import com.allegromini.front.session.CurrentUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizationService {

    private RestTemplate restTemplate;
    @Resource(name = "currentUserSession")
    private CurrentUser currentUser;


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
        loginAccount(accountDTO);
    }

    public void loginAccount(AccountDTO accountDTO) {
        currentUser.setLogin(accountDTO.getEmail());
        currentUser.setPassword(accountDTO.getPassword());
    }

    public void logOut() {
        currentUser.setLogin("");
        currentUser.setPassword("");
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }
}
