package com.epam.labproject.repository;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;
import org.springframework.data.repository.CrudRepository;
import com.epam.labproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Account findByNumber(int number);
    public List<Account> findAllByUser(User user);
}