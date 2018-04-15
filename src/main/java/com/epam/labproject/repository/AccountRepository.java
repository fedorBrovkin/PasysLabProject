package com.epam.labproject.repository;

import com.epam.labproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  public Account findByNumber(int number);
}