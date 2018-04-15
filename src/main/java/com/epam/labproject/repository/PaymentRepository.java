package com.epam.labproject.repository;

import com.epam.labproject.model.entity.CreditCard;
import com.epam.labproject.model.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {
   public List<Payment> findAllBySource(CreditCard source);
   public List<Payment> findAllBySourceAndTarget(CreditCard source, CreditCard tardet);
}
