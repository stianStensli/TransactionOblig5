package com.oblig5.transaction.service;

import com.oblig5.transaction.dao.BuyBtcDao;
import com.oblig5.transaction.dao.SellBtcDao;
import com.oblig5.transaction.dto.SortByPrice;
import com.oblig5.transaction.dto.TransactionDto;
import com.oblig5.transaction.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;

@Service
public class TransactionService {
    @Autowired
    private SellBtcDao sellDao;
    @Autowired
    private BuyBtcDao buyDao;
    @Autowired
    private UserService userService;


    public static Double getBitCoinValue(){
        try {
            URL oracle = new URL("https://blockchain.info/tobtc?currency=USD&value=1");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            Double value = 0.0;
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                try
                {
                    value = Double.parseDouble(inputLine);
                }
                catch(NumberFormatException e) {}
            }
            in.close();

            Double usdBtcRate = value;
            Double BTCValue = 1 / usdBtcRate;

            return BTCValue;
        }catch (Exception ex){
            return null;
        }
    }

    public SellBtc findSellById(Integer id) {
        return sellDao.findById(id).get();
    }

    public BuyBtc findBuyById(Integer id) {
        return buyDao.findById(id).get();
    }

    public void saveSell(SellBtc sell) throws InsufficientFundsException {
        if(sell.getId() == null){
            User seller = sell.getUser();
            if(seller.getWallet().transferBtc(-sell.getAmount())==null){
                throw new InsufficientFundsException("Wallet does not have a sufficient amount of USD to complete transaction.");
            }
            userService.saveUser(seller);
        }else{
            //TODO: Make transaction
        }

        sellDao.save(sell);
    }

    public void saveBuy(BuyBtc buy) throws InsufficientFundsException{
        if(buy.getId() == null){
            User seller = buy.getUser();
            if(seller.getWallet().transferUsd(-buy.getPrice()) == null){
                throw new InsufficientFundsException("Wallet does not have a sufficient amount of BTC to complete transaction.");
            }
            userService.saveUser(seller);
    }else{
        //TODO: Make transaction
    }


        buyDao.save(buy);
    }

    public Iterable<SellBtc> findAllSell() {
        return sellDao.findAll();
    }
    public Iterable<BuyBtc> findAllBuy() {
        return buyDao.findAll();
    }

    public void deleteSellById(Integer Id) {
        sellDao.deleteById(Id);
    }
    public void deleteBuyById(Integer Id) {
        buyDao.deleteById(Id);
    }


    public Iterable<TransactionDto> findAllSellDto() {
        LinkedList<TransactionDto> list = new LinkedList<>();
        for(SellBtc sell :  sellDao.findAll()){
            list.add(new TransactionDto(sell));
        }
        return sortTransactions(list,8);
    }
    public Iterable<TransactionDto> findAllBuyDto() {
        LinkedList<TransactionDto> list = new LinkedList<>();
        for(BuyBtc sell :  buyDao.findAll()){
            list.add(new TransactionDto(sell));
        }
        return sortTransactions(list,8);
    }

    private Iterable<TransactionDto> sortTransactions(LinkedList<TransactionDto> list, int topPriceNr){
        LinkedList<TransactionDto> sortedList = new LinkedList<>();

        TransactionDto[] arr = new TransactionDto[list.size()];
        arr = list.toArray(arr);

        Arrays.sort(arr, new SortByPrice());
        //TODO:Merg

        for(int i=0; i < arr.length; i++){
            if(i==topPriceNr){
                break;
            }
            DecimalFormat format = new DecimalFormat("##.000");
            arr[i].setPrice(Double.parseDouble(format.format(arr[i].getPrice())));
            arr[i].setAmount(Double.parseDouble(format.format(arr[i].getAmount())));
            sortedList.add(arr[i]);
        }
        return sortedList;
    }
}
