package com.oblig5.transaction.service;


import com.oblig5.transaction.dao.UserDao;
import com.oblig5.transaction.dto.UserDto;
import com.oblig5.transaction.model.Currency;
import com.oblig5.transaction.model.User;
import com.oblig5.transaction.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

@Service("userService")
@Transactional
public class UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findById(Integer id) {
        return dao.findById(id).get();
    }

    public User findByEmail(String email) {
        for(User usr : findAllUsers()){
            if(usr.getEmail().equals(email)){
                return usr;
            }
        }
        return null;
    }

    public void saveUser(User user) {
        if(user.getId() == null){
            if(user.getWallet() == null){
                Wallet wallet = new Wallet();

                Map founds = new HashMap();
                founds.put(Currency.USD,1000.0);
                founds.put(Currency.BTC,100.0);
                wallet.setFounds(founds);

                user.setWallet(wallet);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            dao.save(user);
        }else{
            Optional<User> temp = dao.findById(user.getId());
            if(temp.isPresent()){
                if(!user.getPassword().equals(temp.get().getPassword())){
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
                dao.save(user);

            }else{
                //TODO: handle Error
            }

        }
    }

    public void deleteUserById(Integer Id) {
        dao.deleteById(Id);
    }

    public Iterable<User> findAllUsers() {
        return dao.findAll();
    }

    public Iterable<UserDto> findAllUsersDto() {
        LinkedList<UserDto> list = new LinkedList<>();
        for(User user :  dao.findAll()){
            list.add(new UserDto(user));
        }
        return list;
    }

    public boolean isEmailNotUnique(String email) {
        for(User usr : findAllUsers()){
            if(usr.getEmail().equals(email)){
                return true;
            }
        }

        return false;
    }
}