package com.oblig5.transaction.controller;


import com.oblig5.transaction.model.Currency;
import com.oblig5.transaction.model.InsufficientFundsException;
import com.oblig5.transaction.model.Transaction;
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
        Transaction buy = new Transaction();
        buy.setAmountFrom(amount*price);
        buy.setAmountTo(amount);
        buy.setOfferPrice(price);
        buy.setCurrencyTo(Currency.BTC);
        buy.setCurrencyFrom(Currency.USD);

        if(principal == null)
            return "redirect:/";
        User usr = userService.findByEmail(principal.getName());
        buy.setUser(usr);

        try {
            transactionService.save(buy);
        } catch (InsufficientFundsException e) {
            redirectAttrs.addAttribute("error","buy");
        }

        return "redirect:/FrontPage";
    }

    @RequestMapping("sellBTC")
    public String sell(Principal principal, Double price, Double amount, RedirectAttributes redirectAttrs){
        Transaction sell = new Transaction();
        sell.setAmountFrom(amount);
        sell.setInvert(true);
        sell.setAmountTo(amount*price);
        sell.setOfferPrice(price);
        sell.setCurrencyTo(Currency.USD);
        sell.setCurrencyFrom(Currency.BTC);


        if(principal == null)
            return "redirect:/";
        User usr = userService.findByEmail(principal.getName());
        sell.setUser(usr);

        try {
            transactionService.save(sell);
        } catch (InsufficientFundsException e) {
            redirectAttrs.addAttribute("error","sell");
        }
        return "redirect:/FrontPage";
    }
}
