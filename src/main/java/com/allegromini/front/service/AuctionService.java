package com.allegromini.front.service;

import com.allegromini.front.dto.AuctionDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuctionService {
    private AuthorizationService authorizationService;

    private RestTemplate restTemplate;

    public AuctionService(RestTemplate restTemplate, AuthorizationService authorizationService) {
        this.restTemplate = restTemplate;
        this.authorizationService = authorizationService;
    }
    public void addNewAuction(AuctionDTO newAuctionDTO, byte[] bytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("auctionDTO", newAuctionDTO);
        ByteArrayResource fileResource = new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return "exampleFileName.jpg"; // Ustaw nazwę pliku
            }
        };
        body.add("image", fileResource);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        restTemplate.postForEntity("http://localhost:8080/api/v1/auctions", requestEntity, String.class);
    }

    public List<AuctionDTO> getAuctionResponseList() {
        System.out.println("CURRENT USER: " + authorizationService.getCurrentUser()); //todo nalezy to dostarczyc do backendu
        List<AuctionDTO> auctions = restTemplate.getForObject("http://localhost:8080/api/v1/auctions", List.class);
        return auctions;
    }

    public AuctionDTO findAuctionById(int id) {
        return restTemplate.getForObject("http://localhost:8080/api/v1/auctions/" + id, AuctionDTO.class);
    }
}
