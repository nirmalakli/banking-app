package com.nirmal.banking.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private final TransactionType type;
    private final LocalDateTime time;
    private final BigDecimal amount;

    public Transaction(TransactionType type, LocalDateTime time, BigDecimal amount) {
        this.type = type;
        this.time = time;
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type=" + type +
                ", time=" + time +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return type == that.type &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, amount);
    }
}
