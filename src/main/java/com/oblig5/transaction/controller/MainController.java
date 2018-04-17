package com.oblig5.transaction.controller;

import com.oblig5.transaction.configuration.FillDBTestData;
import com.oblig5.transaction.dto.UserDto;
import com.oblig5.transaction.model.User;
import com.oblig5.transaction.model.Wallet;
import com.oblig5.transaction.service.TransactionService;
import com.oblig5.transaction.service.UserService;
import javassist.bytecode.stackmap.BasicBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    public static boolean init = true;

    @RequestMapping("/")
    public String welcome(Principal prin){
        if(init) {
             FillDBTestData.fillDB(userService,transactionService);

            init = false;
        }

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
