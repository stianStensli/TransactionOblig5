package com.oblig5.transaction.dto;

import com.oblig5.transaction.model.User;

public class UserDto {
    private Integer id;

    private String email;

    private String firstName;

    private String lastName;

    public UserDto(){

    }

    public UserDto(User user){
        this.id         = user.getId();
        this.email      = user.getEmail();
        this.firstName  = user.getFirstName();
        this.lastName   = user.getLastName();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
