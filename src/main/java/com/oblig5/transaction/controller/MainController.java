package com.oblig5.transaction.controller;


import com.oblig5.transaction.model.User;
import com.oblig5.transaction.service.TransactionService;
import com.oblig5.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

/***
 * Handles requests for most web pages.
 * May only retrieve a html page, or retrieve data to the view.
 * Not including pages under /transaction
 */
@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    public static boolean init = true;

    @RequestMapping("/")
    public String welcome(Principal prin){

        if(prin != null){
            User user = userService.findByEmail(prin.getName());
            if(user != null){
                return "redirect:/FrontPage";
            }
        }

        return "home";
    }

    @RequestMapping("/FrontPage")
    public String home(Principal prin, Model model,@RequestParam(value = "error") Optional<String> error){
        User user = userService.findByEmail(prin.getName());
        if(user == null){
            return "redirect:/";
        }

        model.addAttribute("name",user.getFirstName() + " " + user.getLastName());
        model.addAttribute("wallet",user.getWallet());


        Double btcValue = TransactionService.getBitCoinValue();
        if(btcValue != null){
            String result = String.format("%.3f", btcValue);
            model.addAttribute("BTC", result);
        }else{
            model.addAttribute("BTC", "N/A");
        }
        if(error.isPresent()){
            if(error.get().equals("buy")){
                model.addAttribute("error","Insufficient funds to offer to buy.");
            }else if(error.get().equals("sell")){
                model.addAttribute("error","Insufficient funds to make sell.");
            }
        }

        return "FrontPage";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
