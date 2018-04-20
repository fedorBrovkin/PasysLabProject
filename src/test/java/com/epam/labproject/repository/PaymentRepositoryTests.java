package com.epam.labproject.repository;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.Payment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentRepositoryTests {

    private static final int TEST_CREDIT_CARD_SOURCE_NUMBER = 54321;

    private static final int TEST_CREDIT_CARD_TARGET_NUMBER = 12345;

    private static final int TEST_ANOTHER_CREDIT_CARD_TARGET_NUMBER = 2223;

    private static final BigDecimal TEST_AMOUNT_1 = new BigDecimal(100);

    private static final BigDecimal TEST_AMOUNT_2 = new BigDecimal(200);

    private static final BigDecimal TEST_AMOUNT_3 = new BigDecimal(300);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    private CreditCard source;

    private CreditCard target;

    private List<Payment> payments;

    @Before
    public void setUp() {
        source = new CreditCard();
        source.setNumber(TEST_CREDIT_CARD_SOURCE_NUMBER);
        creditCardRepository.save(source);

        target = new CreditCard();
        target.setNumber(TEST_CREDIT_CARD_TARGET_NUMBER);
        creditCardRepository.save(target);

        payments = new ArrayList<>();

        Payment payment1 = new Payment();
        payment1.setSource(source);
        payment1.setTarget(target);
        payment1.setAmount(TEST_AMOUNT_1);
        payment1.setTime(LocalDateTime.now());
        Payment savedPayment1 = paymentRepository.save(payment1);

        Payment payment2 = new Payment();
        payment2.setSource(source);
        payment2.setTarget(target);
        payment2.setAmount(TEST_AMOUNT_2);
        payment2.setTime(LocalDateTime.now());
        Payment savedPayment2 = paymentRepository.save(payment2);

        CreditCard anotherTarget = new CreditCard();
        anotherTarget.setNumber(TEST_ANOTHER_CREDIT_CARD_TARGET_NUMBER);
        creditCardRepository.save(anotherTarget);

        Payment payment3 = new Payment();
        payment3.setSource(source);
        payment3.setTarget(anotherTarget);
        payment3.setAmount(TEST_AMOUNT_3);
        payment3.setTime(LocalDateTime.now());
        Payment savedPayment3 = paymentRepository.save(payment3);

        payments.add(savedPayment1);
        payments.add(savedPayment2);
        payments.add(savedPayment3);
    }

    @Test
    public void testFindAllBySource() {
        List<Payment> foundPayments = paymentRepository.findAllBySource(source);
        assertNotNull(foundPayments);
        assertThat(foundPayments, hasSize(3));
        assertThat(foundPayments, containsInAnyOrder(payments.get(0), payments.get(1), payments.get(2)));
    }

    @Test
    public void testFindAllByTarget() {
        List<Payment> foundPayments = paymentRepository.findAllByTarget(target);
        assertNotNull(foundPayments);
        assertThat(foundPayments, hasSize(2));
        assertThat(foundPayments, containsInAnyOrder(payments.get(0), payments.get(1)));
    }

    @Test
    public void testFindAllBySourceAndTarget() {
        List<Payment> foundPayments = paymentRepository.findAllBySourceAndTarget(source, target);
        assertNotNull(foundPayments);
        assertThat(foundPayments, hasSize(2));
        assertThat(foundPayments, containsInAnyOrder(payments.get(0), payments.get(1)));
    }
}
