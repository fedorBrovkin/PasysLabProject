package com.epam.labproject.repository;

import com.epam.labproject.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

  public CreditCard findByNumber(int number);
}