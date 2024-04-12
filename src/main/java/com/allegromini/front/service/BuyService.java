package com.allegromini.front.service;

import com.allegromini.front.dto.AuctionDTO;
import com.allegromini.front.dto.PurchaseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BuyService {
    private RestTemplate restTemplate;
    private AuthorizationService authorizationService;
    private BackendClient backendClient;

    public BuyService(RestTemplate restTemplate, AuthorizationService authorizationService, BackendClient backendClient) {
        this.restTemplate = restTemplate;
        this.authorizationService = authorizationService;
        this.backendClient = backendClient;
    }
    public void buyProduct(int auctionId) {
        backendClient.buyProduct(auctionId);
    }
}
