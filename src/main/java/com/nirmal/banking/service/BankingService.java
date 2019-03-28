package com.nirmal.banking.service;

import com.nirmal.banking.domain.AccountDetails;
import com.nirmal.banking.domain.BankAccount;

import java.math.BigDecimal;

public class BankingService{

    public AccountDetails credit(BankAccount bankAccount, BigDecimal amount) {
        final AccountDetails accountDetails = bankAccount.credit(amount);
        reconcile(accountDetails);
        return accountDetails;
    }

    public AccountDetails debit(BankAccount bankAccount, BigDecimal amount) {
        final AccountDetails accountDetails = bankAccount.debit(amount);
        reconcile(accountDetails);
        return accountDetails;
    }

    private void reconcile(AccountDetails accountDetails) {
        final boolean accountsMatchup = accountDetails.reconcile();
        if(!accountsMatchup) {
            throw new IllegalStateException(String.format("Recon failure! Current Balance = %s\tSum of all transactions = %s", accountDetails.getBalance(), accountDetails.getTransactionBalance()));
        }
    }

    public BigDecimal getBalance(BankAccount bankAccount) {
        return bankAccount.getBalance();
    }

    public AccountDetails getAccountStatement(BankAccount bankAccount) {
        return bankAccount.getAccountDetails();
    }
}
