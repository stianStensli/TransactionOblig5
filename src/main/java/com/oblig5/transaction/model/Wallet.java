package com.oblig5.transaction.model;

import javax.persistence.*;
import java.util.Map;
import java.util.StringJoiner;

/***
 * Linked whit a user, the wallet object stores a users funds.
 * Wallet contains a link between a Currency and a Double, this means that stored currency e.g BTC is linked with a value 13.2
 * Has the transfer method that handles the wallets funds so it can not be set to a negative value.
 */
@Entity
@Table(name = "APP_WALLET")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "WALLET", joinColumns = @JoinColumn(name = "WALLET_id"))
    @MapKeyColumn(name="mapKey")
    @Column(name = "mapValue")
    private Map<Currency, Double> founds;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<Currency, Double> getFounds() {
        return founds;
    }

    public void setFounds(Map<Currency, Double> founds) {
        this.founds = founds;
    }

    public Double transfer(Double amount, Currency currency) throws InsufficientFundsException {
        founds.putIfAbsent(currency,0.0);
        Double current = founds.get(currency);
        Double newValue = current + amount;
        if(newValue < 0){
            throw new InsufficientFundsException("Insufficient amount of "+currency.toString());
        }

        founds.replace(currency,newValue);
        return newValue;
    }

    @Override
    public String toString() {
        StringJoiner build = new StringJoiner(", ","","");
        for (Map.Entry<Currency, Double> entry : founds.entrySet())
        {
            build.add(entry.getKey() + ": " + entry.getValue());
        }
        return build.toString();
    }
}

