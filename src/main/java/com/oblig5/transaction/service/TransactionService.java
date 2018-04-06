package com.oblig5.transaction.service;

import com.oblig5.transaction.dao.BuyBtcDao;
import com.oblig5.transaction.dao.SellBtcDao;
import com.oblig5.transaction.model.BuyBtc;
import com.oblig5.transaction.model.SellBtc;
import com.oblig5.transaction.model.User;
import com.oblig5.transaction.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class TransactionService {
    @Autowired
    private SellBtcDao sellDao;
    @Autowired
    private BuyBtcDao buyDao;

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

    public void saveSell(SellBtc sell) {
        sellDao.save(sell);
    }
    public void saveBuy(SellBtc buy) {
        sellDao.save(buy);
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


}
