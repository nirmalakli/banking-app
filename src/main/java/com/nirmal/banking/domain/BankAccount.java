package com.nirmal.banking.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccount {

    private final String accountNumber;
    private AccountDetails accountDetails = new AccountDetails(BigDecimal.ZERO, Collections.emptyList());

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    public BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountDetails getAccountDetails() {
        try {
            lockReads();
            return accountDetails;
        } finally {
            unlockReads();
        }
    }

    public AccountDetails debit(BigDecimal amount) {
        try {
            lockWrites();
            return this.accountDetails = getAccountDetails().debit(amount);
        } finally {
            unlockWrites();
        }
    }

    public AccountDetails credit(BigDecimal amount) {
        try {
            lockWrites();
            return this.accountDetails = getAccountDetails().credit(amount);
        } finally {
            unlockWrites();
        }
    }

    public BigDecimal getBalance() {
        try {
            lockReads();
            return accountDetails.getBalance();
        } finally {
            unlockReads();
        }
    }

    public List<Transaction> getAccountStatement() {
        try {
            lockReads();
            return accountDetails.getAccountStatement();
        } finally {
            unlockReads();
        }
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountDetails=" + accountDetails +
                '}';
    }

    private void lockReads() {
        readLock.lock();
    }

    private void unlockReads() {
        readLock.unlock();
    }

    private void lockWrites() {
        writeLock.lock();
    }

    private void unlockWrites() {
        writeLock.unlock();
    }
}
