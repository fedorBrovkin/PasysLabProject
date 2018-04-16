package com.epam.labproject.repository;

import com.epam.labproject.model.entity.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, String> {
    public CreditCard findByNumber(int number);
}
