package com.oblig5.transaction.controller;

import com.oblig5.transaction.dto.UserDto;
import com.oblig5.transaction.model.User;
import com.oblig5.transaction.service.TransactionService;
import com.oblig5.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@RestController
public class APIController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/api/users", produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<UserDto> index(){
        return userService.findAllUsersDto();
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
