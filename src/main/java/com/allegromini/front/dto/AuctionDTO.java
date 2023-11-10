package com.allegromini.front.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDTO {
    private int id;
    private String name = "";
    private BigDecimal price;
    private String description = "";
    private String ownerEmail = "";
    private String imageName = "";
    private String imageType = "";
    private String imageURL = "";

}
