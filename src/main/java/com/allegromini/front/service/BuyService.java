package com.allegromini.front.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BuyService {
    private RestTemplate restTemplate;
    private AuthorizationService authorizationService;

    public BuyService(RestTemplate restTemplate, AuthorizationService authorizationService) {
        this.restTemplate = restTemplate;
        this.authorizationService = authorizationService;
    }
    public void buyProduct(int auctionId) {
        System.out.println(auctionId);
    }
}
