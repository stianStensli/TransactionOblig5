package com.oblig5.transaction.configuration;

import com.oblig5.transaction.model.*;
import com.oblig5.transaction.service.TransactionService;
import com.oblig5.transaction.service.UserService;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class FillDBTestData {

    public static void fillDB(UserService userService,TransactionService transactionService){

        Wallet wallet = new Wallet();

        Map founds = new HashMap();
        founds.put(Currency.USD,1000000.0);
        founds.put(Currency.BTC,100000.0);
        wallet.setFounds(founds);

        User admin = new User();
        admin.setEmail("admin");
        admin.setPassword("123");
        admin.setFirstName("Adam");
        admin.setLastName("Admin");
        admin.setWallet(wallet);


        userService.saveUser(admin);
        admin = userService.findByEmail("admin");
/*
        for(int i = 0; i<30;i++){
            if(Math.random() > 0.5 ){
                makeSell(admin, transactionService);
            }else{
                makeBuy(admin, transactionService);
            }
        }*/


    }

    private static void makeSell(User user, TransactionService transactionService){
        Double btcValue = TransactionService.getBitCoinValue();
        if(btcValue == null){
            btcValue = 6102.324;
        }
        Double rngSeed = Math.random();

        Transaction sell = new Transaction();
        DecimalFormat numberFormat = new DecimalFormat("#.000");

        Double diff = 10 * (0.5 - rngSeed);

        Double newValue = btcValue + diff;
        newValue = Double.parseDouble(numberFormat.format(newValue));
        double sellAmount = (10*rngSeed);
        sellAmount = Double.parseDouble(numberFormat.format(sellAmount));
        Double newAmount = Double.parseDouble(numberFormat.format(6102.324 * sellAmount));

        sell.setCurrencyFrom(Currency.BTC);
        sell.setCurrencyTo(Currency.USD);
        sell.setOfferPrice(newValue);
        sell.setInvert(true);

        sell.setAmountFrom(sellAmount);
        sell.setAmountTo(newAmount);
        sell.setUser(user);

        try {
            transactionService.save(sell);
        } catch (InsufficientFundsException e) { }

    }

    private static void makeBuy(User user, TransactionService transactionService){
        Double btcValue = TransactionService.getBitCoinValue();
        if(btcValue == null){
            btcValue = 6102.324;
        }
        Double rngSeed = Math.random();

        Transaction sell = new Transaction();
        DecimalFormat numberFormat = new DecimalFormat("#.000");

        Double diff = 10 * (0.5 - rngSeed);

        Double offer = (btcValue + diff);
        offer = Double.parseDouble(numberFormat.format(offer));

        Double newAmount = Double.parseDouble(numberFormat.format(rngSeed*10));

        sell.setCurrencyFrom(Currency.USD);
        sell.setCurrencyTo(Currency.BTC);
        sell.setOfferPrice(offer);
        sell.setAmountFrom(offer*newAmount);
        sell.setAmountTo(newAmount);
        sell.setUser(user);

        try {
            transactionService.save(sell);
        } catch (InsufficientFundsException e) { }
    }


}
