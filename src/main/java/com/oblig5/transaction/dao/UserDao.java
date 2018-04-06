package com.oblig5.transaction.dao;

import com.oblig5.transaction.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer>{
}