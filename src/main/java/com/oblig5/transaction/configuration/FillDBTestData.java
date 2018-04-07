package com.oblig5.transaction.configuration;

import com.oblig5.transaction.model.*;
import com.oblig5.transaction.service.TransactionService;
import com.oblig5.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class FillDBTestData {

    public static void fillDB(UserService userService,TransactionService transactionService){

        Wallet wallet = new Wallet();

        wallet.setBtc(1000000.0);
        wallet.setUsd(10000000.0);

        User admin = new User();
        admin.setEmail("admin");
        admin.setPassword("123");
        admin.setFirstName("Adam");
        admin.setLastName("Admin");
        admin.setWallet(wallet);


        userService.saveUser(admin);
        admin = userService.findByEmail("admin");

        for(int i = 0; i<30;i++){
            if(Math.random() > 0.5 ){
                makeSell(admin, transactionService);
            }else{
                makeBuy(admin, transactionService);
            }
        }


    }

    private static void makeSell(User user, TransactionService transactionService){
        Double btcValue = TransactionService.getBitCoinValue();
        if(btcValue == null){
            btcValue = 6102.324;
        }
        Double rngSeed = Math.random();

        SellBtc sell = new SellBtc();

        sell.setAmount(5*rngSeed);
        Double diff = 10 * (0.5 - rngSeed);
        sell.setPrice(btcValue + diff);
        sell.setUser(user);

        try {
            transactionService.saveSell(sell);
        } catch (InsufficientFundsException e) { }

    }

    private static void makeBuy(User user, TransactionService transactionService){
        Double btcValue = TransactionService.getBitCoinValue();
        if(btcValue == null){
            btcValue = 6102.324;
        }
        Double rngSeed = Math.random();

        BuyBtc buy = new BuyBtc();

        buy.setAmount(5*rngSeed);
        Double diff = 10 * (0.5 - rngSeed);
        buy.setPrice(btcValue + diff);
        buy.setUser(user);

        try {
            transactionService.saveBuy(buy);
        } catch (InsufficientFundsException e) { }
    }


}
