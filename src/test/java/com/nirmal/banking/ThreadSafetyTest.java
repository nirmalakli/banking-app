package com.nirmal.banking;

import com.nirmal.banking.domain.BankAccount;
import com.nirmal.banking.service.BankingService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;

public class ThreadSafetyTest {

    @Test
    public void concurrentDebitsAndCreditsCancelEachOther() {
        final BankingService bankingService = new BankingService();
        final BankAccount bankAccount = new BankAccount("007");

        final long startTime = System.currentTimeMillis();
        int NUMBER_OF_INSTRUCTIONS = 10_000;

        Runnable debit = () -> bankingService.debit(bankAccount, BigDecimal.ONE);
        Runnable credit = () -> bankingService.credit(bankAccount, BigDecimal.ONE);

        final ExecutorService threadPool = Executors.newFixedThreadPool(10);

        Future[] futures = new Future[2 * NUMBER_OF_INSTRUCTIONS];
        for (int i = 0; i < 2 * NUMBER_OF_INSTRUCTIONS - 1; i += 2) {
            final Future<?> debitFuture = threadPool.submit(debit);
            final Future<?> creditFuture = threadPool.submit(credit);

            futures[i] = debitFuture;
            futures[i + 1] = creditFuture;
        }

        for (Future future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(" Done in " + (System.currentTimeMillis() - startTime) + " ms");
        final BigDecimal balance = bankingService.getBalance(bankAccount);
        System.out.println(" Final Balance : " + balance);
        assertTrue(balance.compareTo(BigDecimal.ZERO) == 0);

        threadPool.shutdownNow();
    }
}
