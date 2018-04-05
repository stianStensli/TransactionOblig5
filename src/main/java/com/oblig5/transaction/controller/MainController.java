package com.oblig5.transaction.controller;

import com.oblig5.transaction.model.User;
import com.oblig5.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    public static boolean init = true;

    @RequestMapping("/")
    public String welcome(){
        if(init) {
            User admin = new User();
            admin.setEmail("admin");
            admin.setPassword("123");
            admin.setFirstName("admin");
            admin.setLastName("admin");

            userService.saveUser(admin);
            init = false;
        }


        return "home";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
