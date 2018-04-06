package com.oblig5.transaction.controller;

import com.oblig5.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction/")
public class TransactionController {
    @Autowired
    private UserService userService;

    @RequestMapping("buyBTC")
    public String buy(){

        return "redirect:/";
    }
    @RequestMapping("sellBTC")
    public String sell(){
        return "redirect:/";
    }
}
