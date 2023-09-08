package com.allegromini.front.controller;

import com.allegromini.front.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AuctionsController {
    private AuctionService auctionService;

    AuctionsController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/all-auctions")
    public String getAuctionMaker(Model model) {
        model.addAttribute("auctions", auctionService.getAuctionResponseList());
        return "all-auctions";
    }

    @GetMapping("/auction/{id}")
    public String getAuction(Model model,@PathVariable int id) {
        model.addAttribute("auction", auctionService.findAuctionById(id));
        return "auction";
    }
}
