package com.epam.labproject.repository;

import com.epam.labproject.model.entity.Account;
import com.epam.labproject.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
    public Account findByNumber(int number);
    public List<Account> findAllByUser(User user);
}
