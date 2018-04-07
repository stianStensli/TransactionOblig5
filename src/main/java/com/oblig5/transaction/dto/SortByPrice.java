package com.oblig5.transaction.dto;

import com.oblig5.transaction.dto.TransactionDto;
import com.oblig5.transaction.service.TransactionService;

import java.util.Comparator;

public class SortByPrice implements Comparator<TransactionDto>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(TransactionDto a, TransactionDto b)
    {
        if(a.getPrice() > b.getPrice()){
            return 1;
        }if(a.getPrice() == b.getPrice()){
            return 0;
    }
    return -1;

    }
}
