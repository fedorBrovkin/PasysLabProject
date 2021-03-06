package com.epam.labproject.repository;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.Payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllBySource(CreditCard source);

    List<Payment> findAllByTarget(CreditCard target);

    List<Payment> findAllBySourceAndTarget(CreditCard source, CreditCard target);
}
