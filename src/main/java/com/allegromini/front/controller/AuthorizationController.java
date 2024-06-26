package com.allegromini.front.controller;

import com.allegromini.front.dto.AccountDTO;
import com.allegromini.front.exception.AuthorizationServiceException;
import com.allegromini.front.service.AuthorizationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/authorization")
    public String getAuthorizationPage() {
        return "authorization.html";
    }

    @PostMapping("/register")
    public String postRegister(String email, String password, String repeatPassword, boolean tos, Model model) {
        try {
            authorizationService.registerAccount(new AccountDTO(email, password, repeatPassword, tos));
        } catch (AuthorizationServiceException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", e.getResponseBodyAsString());
        }
        return "home";
    }

    @PostMapping("/login")
    public String postLogin(String email, String password, Model model) {
        try {
            authorizationService.loginAccount(new AccountDTO(email, password));
            return "home";
        } catch (AuthorizationServiceException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/logout")
    public String getLogout(Model model) {
        authorizationService.logOut();
        model.addAttribute("successMessage", "You have logged out successfully.");
        return "home";
    }
}
