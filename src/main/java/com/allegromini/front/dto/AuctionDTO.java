package com.allegromini.front.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class AuctionDTO {
    private int id;
    private String name = "";
    private BigDecimal price;
    private String description = "";
    private String ownerEmail = "";
    private byte[] image;
    private String imageName = "";
    private String imageType = "";



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }
}
