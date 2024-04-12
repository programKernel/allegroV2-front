package com.allegromini.front.service;

import com.allegromini.front.dto.AuctionDTO;
import com.allegromini.front.dto.PurchaseDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class BackendClient {
    private RestTemplate restTemplate;
    private AuthorizationService authorizationService;

    public BackendClient(RestTemplate restTemplate, AuthorizationService authorizationService) {
        this.restTemplate = restTemplate;
        this.authorizationService = authorizationService;
    }

    public void buyProduct(int auctionId) {
        PurchaseDTO purchaseDTO = new PurchaseDTO(authorizationService.getCurrentUser().getLogin(),auctionId);
        System.out.println(purchaseDTO);
        /*restTemplate.postForEntity("http://localhost:8080/api/v1/purchases", purchaseDTO, String.class);*/
        restTemplate.execute(
                "http://localhost:8080/api/v1/purchases",
                HttpMethod.POST,
                restTemplate.httpEntityCallback(builder().addPurchase(purchaseDTO).addCredentials().build()),
                restTemplate.responseEntityExtractor(String.class),
                String.class);
    }

    public List<AuctionDTO> getAuctions() {
        ResponseEntity<List<AuctionDTO>> auctions = restTemplate.execute(
                "http://localhost:8080/api/v1/auctions",
                HttpMethod.GET,
                restTemplate.httpEntityCallback(builder().addCredentials().build()),
                restTemplate.responseEntityExtractor(List.class),
                List.class);
        return auctions.getBody();
    }

    public AuctionDTO getAuctionById(int id) {
        ResponseEntity<AuctionDTO> auctions = restTemplate.execute(
                "http://localhost:8080/api/v1/auctions/" + id,
                HttpMethod.GET,
                restTemplate.httpEntityCallback(builder().addCredentials().build()),
                restTemplate.responseEntityExtractor(AuctionDTO.class),
                AuctionDTO.class);
        return auctions.getBody();
    }

    public void addNewAuction(AuctionDTO newAuctionDTO, byte[] bytes) {
        newAuctionDTO.setOwnerEmail(authorizationService.getCurrentUser().getLogin());
        restTemplate.execute(
                "http://localhost:8080/api/v1/auctions",
                HttpMethod.POST,
                restTemplate.httpEntityCallback(builder().addAuction(newAuctionDTO).addImage(bytes).addCredentials().build()),
                restTemplate.responseEntityExtractor(String.class),
                String.class);
    }

  /*  private HttpEntity<?> createHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        String credentials = authorizationService.getCurrentUser().getLogin() + ":" + authorizationService.getCurrentUser().getPassword();
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8)));
        if (body != null) {
            return new HttpEntity<>(body, headers);
        }
        return new HttpEntity<>(headers);
    }

    private HttpEntity<?> createHttpEntity() {
        return createHttpEntity(null);
    }*/

    private HttpEntityBuilder builder() {
        return new HttpEntityBuilder();
    }

    class HttpEntityBuilder {
        private HttpHeaders headers = new HttpHeaders();
        private MultiValueMap<String, Object> map =  new LinkedMultiValueMap<>();
        HttpEntityBuilder addCredentials() {
            String credentials = authorizationService.getCurrentUser().getLogin() + ":" + authorizationService.getCurrentUser().getPassword();
            headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8)));
            return this;
        }

        HttpEntityBuilder addImage(byte[] bytes) {
            ByteArrayResource fileResource = new ByteArrayResource(bytes) {
                @Override
                public String getFilename() {
                    return "exampleFileName.jpg"; // Ustaw nazwę pliku
                }
            };
            map.add("image", fileResource);
            return this;
        }

        HttpEntityBuilder addAuction(AuctionDTO auctionDTO){
            map.add("auctionDTO", auctionDTO);
            return this;
        }

        HttpEntityBuilder addPurchase(PurchaseDTO purchaseDTO){
            map.add("purchaseDTO", purchaseDTO);
            return this;
        }

        HttpEntity<?> build() {
            if (map.isEmpty()) {
                return new HttpEntity<>(headers);
            }
            if (map.size() == 1) {
                headers.setContentType(MediaType.APPLICATION_JSON);
                return new HttpEntity<>(map.values().stream().findFirst().get(),headers);
            } else {
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                return new HttpEntity<>(map, headers);
            }
        }
    }
}
