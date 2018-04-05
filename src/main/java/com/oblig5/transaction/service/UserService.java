package com.oblig5.transaction.service;


import com.oblig5.transaction.dao.UserDao;
import com.oblig5.transaction.dto.UserDto;
import com.oblig5.transaction.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    public void updateUser(User user) {
        User entity = dao.findById(user.getId()).get();
        if (entity != null) {
            if (!user.getPassword().equals(entity.getPassword())) {
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
                entity.setPassword(user.getPassword());
            }
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
        }
    }

    //public void deleteUserBySSO(String sso) {     dao.deleteBySSO(sso); }

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