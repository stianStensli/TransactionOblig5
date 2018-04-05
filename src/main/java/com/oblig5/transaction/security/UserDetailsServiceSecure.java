package com.oblig5.transaction.security;

import com.oblig5.transaction.model.User;
import com.oblig5.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceSecure implements UserDetailsService  {

    @Autowired
    private UserService userService;

    public UserDetailsServiceSecure() {
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Iterable<User> users = userService.findAllUsers();
        if(users == null)
            throw new UsernameNotFoundException("User not found by email: " + email);

        for(User user : users){
            if(user.getEmail().equals(email)){
                return toUserDetails(user);
            }
        }
        throw new UsernameNotFoundException("User not found by email: " + email);
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER").build();
    }

}
