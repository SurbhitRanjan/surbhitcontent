package com.cg.ibs.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public class TransactionBean {
	private BigInteger transactionId;
	private TransactionType transactionType;
	private LocalDate transactionDate;
	private BigDecimal transactionAmount;

	public TransactionBean() {
		super();
	}

	public TransactionBean(BigInteger transactionId, TransactionType transactionType, LocalDate transactionDate,
			BigDecimal transactionAmount) {
		super();
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.transactionAmount = transactionAmount;
	}

	public BigInteger getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(BigInteger transactionId) {
		this.transactionId = transactionId;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

}

enum TransactionType {
	CREDIT, DEBIT
}
