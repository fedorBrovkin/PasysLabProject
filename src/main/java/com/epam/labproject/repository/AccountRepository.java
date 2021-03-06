package com.epam.labproject.repository;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByNumber(int number);

    List<Account> findAllByUser(User user);

    List<Account> findAllByUserAndStatusIsTrue(User user);

    List<Account> findAllByUserAndStatusIsFalse(User user);
}