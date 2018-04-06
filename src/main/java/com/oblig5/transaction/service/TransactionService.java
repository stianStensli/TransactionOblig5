package com.oblig5.transaction.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class TransactionService {

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
}
