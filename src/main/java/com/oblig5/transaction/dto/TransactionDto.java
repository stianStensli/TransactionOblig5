package com.oblig5.transaction.dto;

import com.oblig5.transaction.model.BuyBtc;
import com.oblig5.transaction.model.SellBtc;

public class TransactionDto {
    private Integer id;

    private Double price;

    private Double amount;

    public TransactionDto(){}

    public TransactionDto(SellBtc sell){
        this.id = sell.getId();
        this.price = sell.getPrice();
        this.amount = sell.getAmount();
    }
    public TransactionDto(BuyBtc buyBtc){
        this.id = buyBtc.getId();
        this.price = buyBtc.getPrice();
        this.amount = buyBtc.getAmount();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
