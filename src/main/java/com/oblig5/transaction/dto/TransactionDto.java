package com.oblig5.transaction.dto;

import com.oblig5.transaction.model.Transaction;

/***
 *  Transferable object to view.
 *
 *  Is implemented to be able to send transactions to view whiteout sending critical information.
 *  This object is based on the Transaction object, but has attributes like User removed as it is not necessary information for the view
 *  (as of now)
 *  A different TransactionDto may be created to send information about a single transaction that includes to User.
 *
 *  This object will be made up of all Transaction objects whit the same offerPrice when transferred to view.
 *  When this happens the amount will be a sum of all Transaction objects that this object represent.
 */
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
