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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
