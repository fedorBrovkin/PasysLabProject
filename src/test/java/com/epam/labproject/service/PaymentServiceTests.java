package com.epam.labproject.service;

import com.epam.labproject.entity.CreditCard;
import com.epam.labproject.entity.Payment;
import com.epam.labproject.repository.AccountRepository;
import com.epam.labproject.repository.CreditCardRepository;
import com.epam.labproject.repository.PaymentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceTests {

    private static final int TEST_CREDIT_CARD_SOURCE_NUMBER = 54321;

    private static final int TEST_CREDIT_CARD_TARGET_NUMBER = 12345;

    private static final int TEST_ANOTHER_CREDIT_CARD_TARGET_NUMBER = 2223;

    private static final BigDecimal TEST_AMOUNT_1 = new BigDecimal(100);

    private static final BigDecimal TEST_AMOUNT_2 = new BigDecimal(200);

    private static final BigDecimal TEST_AMOUNT_3 = new BigDecimal(300);

    private Payment payment;

    @Autowired
    private CreditCardRepository creditCardRepository;

    private List<Payment> payments;

    private CreditCard source;

    private CreditCard target;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private PaymentService paymentService;

    @Before
    public void setUp() {
        source = new CreditCard();
        source.setNumber(TEST_CREDIT_CARD_SOURCE_NUMBER);

        target = new CreditCard();
        target.setNumber(TEST_CREDIT_CARD_TARGET_NUMBER);


        payment = new Payment();
        payment.setSource(source);
        payment.setTarget(target);
        payment.setAmount(new BigDecimal(2));

        payments = new ArrayList<>();

        Payment payment1 = new Payment();
        payment1.setSource(source);
        payment1.setTarget(target);
        payment1.setAmount(TEST_AMOUNT_1);
        payment1.setTime(LocalDateTime.now());

        Payment payment2 = new Payment();
        payment2.setSource(source);
        payment2.setTarget(target);
        payment2.setAmount(TEST_AMOUNT_2);
        payment2.setTime(LocalDateTime.now());

        CreditCard anotherTarget = new CreditCard();
        anotherTarget.setNumber(TEST_ANOTHER_CREDIT_CARD_TARGET_NUMBER);

        Payment payment3 = new Payment();
        payment3.setSource(source);
        payment3.setTarget(anotherTarget);
        payment3.setAmount(TEST_AMOUNT_3);
        payment3.setTime(LocalDateTime.now());

        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);

        Mockito.when(paymentRepository.findAllBySource(source))
                .thenReturn(payments);

        Mockito.when(paymentRepository.findAllByTarget(target))
                .thenReturn(payments);
    }

    @Test
    public void testFindAllBySource() {
        List<Payment> allBySource = paymentService.findAllBySource(source);

        assertNotNull(allBySource);
        payments.sort(Comparator.comparing(Payment::getTime));

        verify(paymentRepository, times(1)).findAllBySource(source);

        assertThat(allBySource, hasSize(3));

        assertTrue(allBySource.get(0).equals(payments.get(0)));
        assertTrue(allBySource.get(1).equals(payments.get(1)));
        assertTrue(allBySource.get(2).equals(payments.get(2)));
    }

    @Test
    public void testFindAllByTarget() {
        List<Payment> allByTarget = paymentService.findAllByTarget(target);

        assertNotNull(allByTarget);
        payments.sort(Comparator.comparing(Payment::getTime));

        verify(paymentRepository, times(1)).findAllByTarget(target);

        assertThat(allByTarget, hasSize(3));

        assertTrue(allByTarget.get(0).equals(payments.get(0)));
        assertTrue(allByTarget.get(1).equals(payments.get(1)));
        assertTrue(allByTarget.get(2).equals(payments.get(2)));
    }

    @Test
    public void testSave() {
        paymentService.save(payment);

        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testCheckDestinationIsTrue() {
        assertTrue(paymentService.checkDestination(payment, source));
    }

    @Test
    public void testCheckDestinationIsFalse() {
        assertFalse(paymentService.checkDestination(payment, target));
    }
}