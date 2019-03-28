package com.nirmal.banking.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionTest {

    @Test
    public void testFields() {
        final Transaction transaction = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), BigDecimal.ZERO);
        final Transaction transaction2 = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), BigDecimal.ZERO);

        assertNotNull(transaction.toString());
        assertEquals(transaction.hashCode(), transaction2.hashCode());
    }
}