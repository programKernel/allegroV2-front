package com.allegromini.front.controller;

import com.allegromini.front.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class AuctionsController {
    private AuctionService auctionService;

    AuctionsController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/all-auctions")
    public String getAuctionMaker(Model model) {
        try {
            model.addAttribute("auctions", auctionService.getAuctionResponseList());
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", e.getResponseBodyAsString());
        }
        return "all-auctions";
    }

    @GetMapping("/auction/{id}")
    public String getAuction(Model model,@PathVariable int id) {
        try {
            model.addAttribute("auction", auctionService.findAuctionById(id));
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", e.getResponseBodyAsString());
        }
        return "auction";
    }
}
