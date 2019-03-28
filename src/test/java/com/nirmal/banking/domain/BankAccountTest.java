package com.nirmal.banking.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BankAccountTest {

    @Test
    public void testFields() {
        final BankAccount bankAccount = new BankAccount("ABCD");
        bankAccount.credit(BigDecimal.ONE);
        assertNotNull(bankAccount.toString());
    }

    @Test
    public void verifyAccountStatement() {
        final BankAccount bankAccount = new BankAccount("ABCD");
        bankAccount.credit(BigDecimal.ONE);
        final Transaction deposit = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), BigDecimal.ONE);
        assertEquals(Collections.singletonList(deposit), bankAccount.getAccountStatement());
    }
}