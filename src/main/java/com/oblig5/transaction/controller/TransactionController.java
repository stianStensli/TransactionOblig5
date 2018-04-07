package com.oblig5.transaction.controller;

import com.oblig5.transaction.model.BuyBtc;
import com.oblig5.transaction.model.InsufficientFundsException;
import com.oblig5.transaction.model.SellBtc;
import com.oblig5.transaction.model.User;
import com.oblig5.transaction.service.TransactionService;
import com.oblig5.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/transaction/")
public class TransactionController {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("buyBTC")
    public String buy(Principal principal, Double price, Double amount, RedirectAttributes redirectAttrs){
        BuyBtc buy = new BuyBtc();
        buy.setAmount(amount);
        buy.setPrice(price);

        if(principal == null)
            return "redirect:/";
        User usr = userService.findByEmail(principal.getName());
        buy.setUser(usr);

        try {
            transactionService.saveBuy(buy);
        } catch (InsufficientFundsException e) {
            redirectAttrs.addAttribute("Error","buy");
        }

        return "redirect:/";
    }

    @RequestMapping("sellBTC")
    public String sell(Principal principal, Double price, Double amount, RedirectAttributes redirectAttrs){
        SellBtc sell = new SellBtc();
        sell.setAmount(amount);
        sell.setPrice(price);

        if(principal == null)
            return "redirect:/";
        User usr = userService.findByEmail(principal.getName());
        sell.setUser(usr);

        try {
            transactionService.saveSell(sell);
        } catch (InsufficientFundsException e) {
            redirectAttrs.addAttribute("Error","sell");
        }
        return "redirect:/";
    }
}
