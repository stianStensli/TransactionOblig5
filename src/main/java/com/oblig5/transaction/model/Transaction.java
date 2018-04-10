package com.oblig5.transaction.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;


/**
 * Information about a single transaction request. There need to be atleast two to complete a Transaction from one currency to another.
 * The class contains information about the transaction.
 *
 * currencyFrom: Type of currency that the transaction is selling.
 *
 * currencyFrom: Type of currency that the transaction requests to buy.
 *
 * amount: Amount of currency that the transaction requests to buy.
 *
 * offerPrice: Defined as the value per amount that the transaction offers to buy at.
 *
 * e.g: currencyFrom = USD currencyTO = BTC then price is reprecenting the value
 */
@Entity
@Table(name = "APP_TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "OFFER_PRICE", nullable = false)
    private Double offerPrice;

    @NonNull
    @Column(name = "ISINVERT", nullable = false)
    private boolean isInvert = false;

    @NonNull
    @Column(name = "AMOUNTTO", nullable = false)
    private Double amountTo;

    @NonNull
    @Column(name = "AMOUNTFROM", nullable = false)
    private Double amountFrom;

    @NonNull
    @Column(name = "CURRENCY_FROM", nullable = false)
    private Currency currencyFrom;

    @NonNull
    @Column(name = "CURRENCY_TO", nullable = false)
    private Currency currencyTo;

    @NonNull
    @ManyToOne
    private User user;

    public void doTransactino(Transaction transaction){
        if(currencyFrom.equals(transaction.currencyTo) && currencyTo.equals(transaction.currencyFrom)){
            try {
                if(amountFrom <= transaction.amountTo){
                    user.getWallet().transfer(amountTo, currencyTo);
                    transaction.getUser().getWallet().transfer(amountFrom, currencyFrom);
                    transaction.amountTo -= amountFrom;
                    transaction.amountFrom -= amountTo;

                    amountTo = 0.0;
                    amountFrom = 0.0;
                }else{
                    user.getWallet().transfer(transaction.amountFrom, currencyTo);
                    transaction.getUser().getWallet().transfer(transaction.amountTo, currencyFrom);
                    amountTo -= transaction.amountFrom;
                    amountFrom -= transaction.amountTo;

                    transaction.amountTo = 0.0;
                    transaction.amountFrom = 0.0;

                }
            } catch (InsufficientFundsException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Double price) {
        this.offerPrice = price;
    }

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(Currency currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public Currency getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(Currency currencyTo) {
        this.currencyTo = currencyTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Double amountTo) {
        this.amountTo = amountTo;
    }

    public Double getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(Double amountFrom) {
        this.amountFrom = amountFrom;
    }

    public boolean isInvert() {
        return isInvert;
    }

    public void setInvert(boolean invert) {
        isInvert = invert;
    }

}