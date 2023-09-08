package com.allegromini.front.controller;

import com.allegromini.front.dto.AuctionDTO;
import com.allegromini.front.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Controller
public class AuctionMakerController {
    private AuctionService auctionService;

    AuctionMakerController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/auctionmaker")
    public String getAuctionMaker() {
        return "auctionmaker";
    }

    @PostMapping("/createauction")
    public String postCreateAuction(String ownerEmail, String name, BigDecimal price, String description, Model model,
                                    @RequestParam("image") MultipartFile file) {
        try {
            String[] filenameSplit = file.getContentType().split("/");
            AuctionDTO auctionDTO = AuctionDTO.builder()
                    .ownerEmail(ownerEmail)
                    .name(name)
                    .price(price)
                    .description(description)
                    .image(file.getBytes())
                    .imageType("." + filenameSplit[filenameSplit.length - 1])
                    .build();
            auctionService.addNewAuction(auctionDTO);
        } catch (IOException e) {
            e.printStackTrace();
            return "auction-error";
        }
        model.addAttribute("auctionAddedMessage", "The auction has been added.");
        return "home";
    }
}
