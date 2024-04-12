package com.allegromini.front.dto;

import java.time.LocalDateTime;

public class PurchaseDTO {
    private int id;
    private String buyerId;
    private int auctionId;

    public PurchaseDTO(String login, int auctionId) {
        this.buyerId = login;
        this.auctionId = auctionId;
    }

    public PurchaseDTO() {

    }

    public int getId() {
        return id;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    @Override
    public String toString() {
        return "PurchaseDTO{" +
                "id=" + id +
                ", buyerId='" + buyerId + '\'' +
                ", auctionId=" + auctionId +
                '}';
    }
}
