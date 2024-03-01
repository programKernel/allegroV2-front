package com.allegromini.front.controller;

import com.allegromini.front.service.BuyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BuyController {
    private BuyService buyService;

    BuyController(BuyService buyService) {
        this.buyService = buyService;
    }
    @PostMapping("/buy/{auctionId}")
    public String postBuy(Model model, @PathVariable int auctionId) {
        buyService.buyProduct(auctionId);
        model.addAttribute("successMessage", "The item has been purchased successfully.");
        return "home";
    }
}
