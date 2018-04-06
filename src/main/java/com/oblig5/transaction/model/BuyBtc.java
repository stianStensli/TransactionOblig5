package com.oblig5.transaction.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "APP_BUY")
public class BuyBtc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "PRICE", nullable = false)
    private Double price;

    @NonNull
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @NonNull
    @ManyToOne
    private User user;
}
