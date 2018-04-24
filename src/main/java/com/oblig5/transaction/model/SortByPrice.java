package com.oblig5.transaction.model;

import com.oblig5.transaction.dto.TransactionDto;

import java.util.Comparator;

public class SortByPrice implements Comparator<TransactionDto>
{
    public int compare(TransactionDto a, TransactionDto b)
    {
        if(a.getOfferPrice() > b.getOfferPrice()){
            return 1;
        }if(a.getOfferPrice() == b.getOfferPrice()){
            return 0;
    }
    return -1;

    }
}
