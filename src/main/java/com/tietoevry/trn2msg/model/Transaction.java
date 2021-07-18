package com.tietoevry.trn2msg.model;

import com.tietoevry.trn2msg.enums.Currency;
import com.tietoevry.trn2msg.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private TransactionType transactionType;
    private String accNumber;
    private LocalDateTime transactionTime;
    private BigDecimal amount;
    private Currency currency;

    public Transaction(TransactionType transactionType, String accNumber,
                       LocalDateTime transactionTime, BigDecimal amount,
                       Currency currency) {
        this.transactionType = transactionType;
        this.accNumber = accNumber;
        this.transactionTime = transactionTime;
        this.amount = amount;
        this.currency = currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionType == that.transactionType
                && Objects.equals(accNumber, that.accNumber)
                && Objects.equals(transactionTime, that.transactionTime)
                && Objects.equals(amount, that.amount)
                && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionType, accNumber, transactionTime, amount, currency);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionType=" + transactionType +
                ", accNumber='" + accNumber + '\'' +
                ", transactionTime=" + transactionTime +
                ", amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
