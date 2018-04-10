package com.oblig5.transaction.service;

import com.oblig5.transaction.dao.TransactionDao;
import com.oblig5.transaction.dto.TransactionDto;
import com.oblig5.transaction.model.SortByPrice;
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
    private TransactionDao transactionDao;
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

    public Transaction findById(Integer id){
        return transactionDao.findById(id).get();
    }

    public void save(Transaction transaction)throws InsufficientFundsException{
        if(transaction.getId() == null){
            User user = transaction.getUser();
            user.getWallet().transfer(-transaction.getAmountFrom(),transaction.getCurrencyFrom());

            userService.saveUser(user);
        }else{
            //TODO: Make transaction
        }

        transactionDao.save(transaction);
    }

    public Iterable<Transaction> findAll() {
        return transactionDao.findAll();
    }

    public void deleteById(Integer Id) {
        transactionDao.deleteById(Id);
    }

    public Iterable<TransactionDto> findAllSell(Currency currency) {
        return findAllSell(currency, -1);
    }

    public Iterable<TransactionDto> findAllSell(Currency currency,  int numberOfTransactions) {
        LinkedList<TransactionDto> list = new LinkedList<>();
        for(Transaction sell :  transactionDao.findAll()){
            if(sell.getCurrencyFrom() == currency){
                list.add(new TransactionDto(sell,true));
            }
        }
        return sortTransactions(list, numberOfTransactions);
    }

    public Iterable<TransactionDto> findAllBuy(Currency currency) {
        return findAllBuy(currency, -1);

    }

    public Iterable<TransactionDto> findAllBuy(Currency currency,  int numberOfTransactions) {
        LinkedList<TransactionDto> list = new LinkedList<>();
        for(Transaction buy :  transactionDao.findAll()){
            if(buy.getCurrencyTo() == currency){
                list.add(new TransactionDto(buy, false));
            }
        }
        return sortTransactions(list, numberOfTransactions);
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
            sortedList.add(arr[i]);
        }
        return sortedList;
    }
}
