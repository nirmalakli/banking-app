package com.nirmal.banking.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountDetails {
    private final BigDecimal balance;
    private final List<Transaction> transactions;

    public AccountDetails(BigDecimal balance, List<Transaction> transactions) {
        this.balance = balance;
        this.transactions = Collections.unmodifiableList(transactions);
    }

    public AccountDetails credit(BigDecimal amount) {
        final BigDecimal newBalance = this.getBalance().add(amount);
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), amount);
        final ArrayList<Transaction> currentTransactions = new ArrayList<>(this.getAccountStatement());
        currentTransactions.add(creditTransaction);
//        System.out.println("credit " + amount);
        return new AccountDetails(newBalance, currentTransactions);
    }

    public AccountDetails debit(BigDecimal amount) {
        final BigDecimal newBalance = this.getBalance().subtract(amount);
        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), amount);
        final ArrayList<Transaction> currentTransactions = new ArrayList<>(this.getAccountStatement());
        currentTransactions.add(debitTransaction);
//        System.out.println("debit " + amount);
        return new AccountDetails(newBalance, currentTransactions);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getAccountStatement() {
        return Collections.unmodifiableList(transactions);
    }


    public boolean reconcile() {
        return balance.compareTo(getTransactionBalance()) == 0;
    }

    public BigDecimal getTransactionBalance() {
        return transactions.stream()
                .map(transaction -> getSignedAmount(transaction))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getSignedAmount(Transaction transaction) {
        switch (transaction.getType()) {
            case CREDIT:
                return transaction.getAmount();
            case DEBIT:
                return transaction.getAmount().negate();
            default:
                throw new IllegalStateException("Unknown type of transaction : " + transaction.getType());
        }
    }


    @Override
    public String toString() {
        return "AccountDetails{" +
                "balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
