package com.oblig5.transaction.controller;

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

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    public static boolean init = true;

    @RequestMapping("/")
    public String welcome(Principal prin){
        if(init) {
            User admin = new User();
            admin.setEmail("admin");
            admin.setPassword("123");
            admin.setFirstName("admin");
            admin.setLastName("admin");

            userService.saveUser(admin);
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

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/FrontPage")
    public String home(Principal prin, Model model){
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
            model.addAttribute("BTC", "NA");
        }
        return "FrontPage";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
