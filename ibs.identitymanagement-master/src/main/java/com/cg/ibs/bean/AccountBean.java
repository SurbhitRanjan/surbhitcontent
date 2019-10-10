package com.cg.ibs.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class AccountBean {
	private String accountNumber;
	private Set<CustomerBean> accountHolders;
	private BigDecimal currentBalance;
	private String transactionPassword;
	private List<TransactionBean> transactions;

	public AccountBean() {
		super();
	}

	public AccountBean(String accountNumber, Set<CustomerBean> accountHolders, BigDecimal currentBalance,
			String transactionPassword, List<TransactionBean> transactions) {
		super();
		this.accountNumber = accountNumber;
		this.accountHolders = accountHolders;
		this.currentBalance = currentBalance;
		this.transactionPassword = transactionPassword;
		this.transactions = transactions;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String string) {
		this.accountNumber = string;
	}

	public Set<CustomerBean> getAccountHolders() {
		return accountHolders;
	}

	public void setAccountHolders(Set<CustomerBean> accountHolders) {
		this.accountHolders = accountHolders;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}

	public List<TransactionBean> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionBean> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountBean [accountNumber=");
		builder.append(accountNumber);
		builder.append(", currentBalance=");
		builder.append(currentBalance);
		builder.append("]");
		return builder.toString();
	}

	

}
