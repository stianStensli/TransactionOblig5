package com.oblig5.transaction.dto;


import com.oblig5.transaction.model.Transaction;

public class TransactionDto {

    private Double offerPrice;

    private Double amount;

    private boolean isInvert = false;

    public TransactionDto(){

    }

    public TransactionDto(Transaction transaction, boolean from){
        offerPrice = transaction.getOfferPrice();
        if(from)
        amount = transaction.getAmountFrom();
        else{
            amount = transaction.getAmountTo();
        }
        isInvert = transaction.isInvert();

    }
    public Double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Double price) {
        this.offerPrice = price;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isInvert() {
        return isInvert;
    }

    public void setInvert(boolean invert) {
        isInvert = invert;
    }
}
