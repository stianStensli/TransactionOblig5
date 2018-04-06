package com.oblig5.transaction.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "APP_WALLET")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "BTC", nullable = false)
    private Double btc;

    @NonNull
    @Column(name = "USD", nullable = false)
    private Double usd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBtc() {
        return btc;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public Double getUsd() {
        return usd;
    }

    public void setUsd(Double usd) {
        this.usd = usd;
    }
}
