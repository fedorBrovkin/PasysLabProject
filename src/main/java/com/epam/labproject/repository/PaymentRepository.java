package com.epam.labproject.repository;

import com.epam.labproject.model.entity.CreditCard;
import com.epam.labproject.model.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {
   public List<CreditCard> findAllBySource(CreditCard source);
   public List<CreditCard> findAllBySourceAndTarget(CreditCard source, CreditCard tardet);
}
