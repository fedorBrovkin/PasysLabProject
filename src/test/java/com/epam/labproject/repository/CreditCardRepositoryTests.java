package com.epam.labproject.repository;

import com.epam.labproject.entity.CreditCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CreditCardRepositoryTests {

    private static final int TEST_CREDIT_CARD_NUMBER = 54321;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Test
    public void testFindByNumber() {
        CreditCard creditCard = new CreditCard();
        creditCard.setNumber(TEST_CREDIT_CARD_NUMBER);
        creditCardRepository.save(creditCard);

        CreditCard found = creditCardRepository.findByNumber(TEST_CREDIT_CARD_NUMBER);

        assertThat(found.getNumber())
                .isEqualTo(creditCard.getNumber());
    }
}
