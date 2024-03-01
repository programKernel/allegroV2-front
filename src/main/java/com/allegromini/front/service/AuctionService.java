package com.allegromini.front.service;

import com.allegromini.front.dto.AuctionDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuctionService {
    private BackendClient backendClient;
    AuctionService(BackendClient backendClient) {
        this.backendClient = backendClient;
    }
    public void addNewAuction(AuctionDTO newAuctionDTO, byte[] bytes) {
        backendClient.addNewAuction(newAuctionDTO, bytes);
    }

    public List<AuctionDTO> getAuctionResponseList() {
        return backendClient.getAuctions();
    }

    public AuctionDTO findAuctionById(int id) {
        return backendClient.getAuctionById(id);
    }
}
