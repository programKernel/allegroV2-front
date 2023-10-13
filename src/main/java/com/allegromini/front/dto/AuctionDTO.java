package com.allegromini.front.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class AuctionDTO {
    private int id;
    private String name = "";
    private BigDecimal price;
    private String description = "";
    private String ownerEmail = "";
    private String imageName = "";
    private String imageType = "";
}
