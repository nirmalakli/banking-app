package com.nirmal.banking.service;

import com.nirmal.banking.domain.AccountDetails;
import com.nirmal.banking.domain.BankAccount;
import com.nirmal.banking.domain.Transaction;
import com.nirmal.banking.domain.TransactionType;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class BankingServiceTest {

    private final BankAccount bankAccount = new BankAccount("007");
    private final BankingService bankingService = new BankingService();
    private final BigDecimal _100_RS = new BigDecimal("100.0");

    @Test
    public void creditTxUpdatesAccountBalance() {
        bankingService.credit(bankAccount, _100_RS);
        assertTrue(bankingService.getBalance(bankAccount).compareTo(_100_RS) == 0);
    }

    @Test
    public void creditTxUpdatesAccountStatement() {
        bankingService.credit(bankAccount, _100_RS);
        final List<Transaction> accountStatement = bankingService.getAccountStatement(bankAccount).getAccountStatement();
        assertNotNull(accountStatement.get(0).toString());
        assertNotNull(accountStatement.get(0).getTime());
        assertEquals(Collections.singletonList(new Transaction(TransactionType.CREDIT, LocalDateTime.now(), _100_RS)), accountStatement);
    }

    @Test
    public void debitTxUpdatesAccountBalance() {
        bankingService.debit(bankAccount, _100_RS);
        assertTrue(bankingService.getBalance(bankAccount).compareTo(_100_RS.negate()) == 0);
    }

    @Test
    public void debitTxUpdatesAccountStatement() {
        bankingService.debit(bankAccount, _100_RS);
        final List<Transaction> accountStatement = bankingService.getAccountStatement(bankAccount).getAccountStatement();
        assertEquals(Collections.singletonList(new Transaction(TransactionType.DEBIT, LocalDateTime.now(), _100_RS)), accountStatement);
    }
}