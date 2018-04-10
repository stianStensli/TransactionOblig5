package com.oblig5.transaction.dao;

import com.oblig5.transaction.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionDao  extends CrudRepository<Transaction, Integer> {
}
