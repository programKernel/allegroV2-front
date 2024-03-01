package com.allegromini.front.service;

import com.allegromini.front.dto.AuctionDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class BackendClient {
    private RestTemplate restTemplate;
    private AuthorizationService authorizationService;

    public BackendClient(RestTemplate restTemplate, AuthorizationService authorizationService) {
        this.restTemplate = restTemplate;
        this.authorizationService = authorizationService;
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
        private MultiValueMap<String, Object> body = null;
        HttpEntityBuilder addCredentials() {
            String credentials = authorizationService.getCurrentUser().getLogin() + ":" + authorizationService.getCurrentUser().getPassword();
            headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8)));
            return this;
        }

        HttpEntityBuilder addImage(byte[] bytes) {
            prepareMultipartBody();
            ByteArrayResource fileResource = new ByteArrayResource(bytes) {
                @Override
                public String getFilename() {
                    return "exampleFileName.jpg"; // Ustaw nazwę pliku
                }
            };
            body.add("image", fileResource);
            return this;
        }

        HttpEntityBuilder addAuction(AuctionDTO auctionDTO){
            prepareMultipartBody();
            body.add("auctionDTO", auctionDTO);
            return this;
        }

        private void prepareMultipartBody() {
            if (body == null) {
                body = new LinkedMultiValueMap<>();
            }
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        }

        HttpEntity<?> build() {
            if (body != null) {
                return new HttpEntity<>(body, headers);
            }
            return new HttpEntity<>(headers);
        }
    }
}
