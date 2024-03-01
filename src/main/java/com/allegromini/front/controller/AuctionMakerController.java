package com.allegromini.front.controller;

import com.allegromini.front.dto.AuctionDTO;
import com.allegromini.front.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
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
    public String postCreateAuction(String name, BigDecimal price, String description, Model model,
                                    @RequestParam("image") MultipartFile file) {
        try {
            String fileType = file.getContentType();
            String[] nameAndType = fileType.split("/");
            AuctionDTO auctionDTO = AuctionDTO.builder()
                    .name(name)
                    .price(price)
                    .description(description)
                    .imageName(nameAndType[0])
                    .imageType(nameAndType[1])
                    .build();
            auctionService.addNewAuction(auctionDTO, file.getBytes());
            model.addAttribute("successMessage", "The auction has been added.");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", e.getResponseBodyAsString());
        }
        return "home";
    }
}
