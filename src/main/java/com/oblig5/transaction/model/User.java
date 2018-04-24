package com.oblig5.transaction.model;


import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NonNull
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @NonNull
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @NonNull
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }


}