package com.edufect.model;

public class CustomerReport {
	private long accId;
	private int custCode;
	private long totalTransactions;
	private long currentBalance;

	public CustomerReport(long accId, int custCode, long totalTransactions, long currentBalance) {
		super();
		this.accId = accId;
		this.custCode = custCode;
		this.totalTransactions = totalTransactions;
		this.currentBalance = currentBalance;
	}

	public long getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(long totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public long getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(long currentBalance) {
		this.currentBalance = currentBalance;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	public int getCustCode() {
		return custCode;
	}

	public void setCustCode(int custCode) {
		this.custCode = custCode;
	}

}
