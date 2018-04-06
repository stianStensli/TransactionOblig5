package com.oblig5.transaction.controller;

import com.oblig5.transaction.dto.TransactionDto;
import com.oblig5.transaction.dto.UserDto;
import com.oblig5.transaction.model.BuyBtc;
import com.oblig5.transaction.model.SellBtc;
import com.oblig5.transaction.model.User;
import com.oblig5.transaction.service.TransactionService;
import com.oblig5.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(path = "/api/users", produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<UserDto> index(){
        return userService.findAllUsersDto();
    }

    @RequestMapping(path = "/api/transaction/sells", produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<TransactionDto> sell(){
        return transactionService.findAllSellDto();
    }
    @RequestMapping(path = "/api/transaction/buys", produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<TransactionDto> buy(){
        return transactionService.findAllBuyDto();
    }


    //Only for testing: should be removed
    @RequestMapping(path = "/api/usersReal", produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<User> indexReal(){
        return userService.findAllUsers();
    }


    @RequestMapping(value = "/api/users/add" , method = RequestMethod.POST)
    public @ResponseBody
    UserDto save(@RequestBody User jsonString) {
        if(userService.isEmailNotUnique(jsonString.getEmail())){
            return new UserDto();
        }

        userService.saveUser(jsonString);
        UserDto dto = new UserDto(jsonString);

        return dto;
    }

    @RequestMapping(path = "/api/bitCoinValue")
    public @ResponseBody
    Double btcValue(){
        return TransactionService.getBitCoinValue();
    }


}
