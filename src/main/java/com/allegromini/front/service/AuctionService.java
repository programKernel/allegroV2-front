package com.allegromini.front.service;

import com.allegromini.front.dto.AuctionDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class AuctionService {
    private RestTemplate restTemplate;
    private AuthorizationService authorizationService;

    public AuctionService(RestTemplate restTemplate, AuthorizationService authorizationService) {
        this.restTemplate = restTemplate;
        this.authorizationService = authorizationService;
    }
    public void addNewAuction(AuctionDTO newAuctionDTO, byte[] bytes) {
        newAuctionDTO.setOwnerEmail(authorizationService.getCurrentUser().getLogin());
        HttpHeaders headers = new HttpHeaders();
        String credentials = authorizationService.getCurrentUser().getLogin() + ":" + authorizationService.getCurrentUser().getPassword();
        headers.add("Authorization","Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8)));
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
        restTemplate.execute(
                "http://localhost:8080/api/v1/auctions",
                HttpMethod.POST,
                restTemplate.httpEntityCallback(requestEntity),
                restTemplate.responseEntityExtractor(String.class),
                String.class);
    }

    public List<AuctionDTO> getAuctionResponseList() {
        HttpHeaders headers = new HttpHeaders();
        String credentials = authorizationService.getCurrentUser().getLogin() + ":" + authorizationService.getCurrentUser().getPassword();
        headers.add("Authorization","Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8)));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<List<AuctionDTO>> auctions = restTemplate.execute(
                "http://localhost:8080/api/v1/auctions",
                HttpMethod.GET,
                restTemplate.httpEntityCallback(httpEntity),
                restTemplate.responseEntityExtractor(List.class),
                List.class);
        return auctions.getBody();
    }

    public AuctionDTO findAuctionById(int id) {
        HttpHeaders headers = new HttpHeaders();
        String credentials = authorizationService.getCurrentUser().getLogin() + ":" + authorizationService.getCurrentUser().getPassword();
        headers.add("Authorization","Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8)));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<AuctionDTO> auctions = restTemplate.execute(
                "http://localhost:8080/api/v1/auctions/" + id,
                HttpMethod.GET,
                restTemplate.httpEntityCallback(httpEntity),
                restTemplate.responseEntityExtractor(AuctionDTO.class),
                AuctionDTO.class);
        return auctions.getBody();
    }
}
