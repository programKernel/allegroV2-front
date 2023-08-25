package com.allegromini.front.service;

import com.allegromini.front.dto.RegisterDTO;
import com.allegromini.front.exception.AuthorizationServiceException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public void registerAccount(RegisterDTO registerDTO) {
        if (!registerDTO.getRepeatPassword().equals(registerDTO.getPassword())) {
            throw new AuthorizationServiceException("The passwords need to be the same.");
        }
        if (!registerDTO.isTos()) {
            throw new AuthorizationServiceException("You need to accept the Terms Of Service.");
        }
        System.out.println("Connecting with back...");
        //todo connect with backend
    }

    /*public void loginAccount(LoginRequest loginRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(loginRequest.getEmail());
        if (optionalAccount.isEmpty() || !optionalAccount.get().getPassword().equals(loginRequest.getPassword())) { //todo dodać hashowanie hasła
            throw new AuthorizationServiceException("The email/password is incorrect.");
        }
        System.out.println("Logging in.");
    }*/
}
