package com.edufect.model;

public class BranchReport {
	private int branchCode;
	private long accounts;
	private long balance;
	private long txncount;

	public BranchReport(int branchCode, long accounts, long balance, long txncount) {
		super();
		this.branchCode = branchCode;
		this.accounts = accounts;
		this.balance = balance;
		this.txncount = txncount;
	}

	public int getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}

	public long getAccounts() {
		return accounts;
	}

	public void setAccounts(long accounts) {
		this.accounts = accounts;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getTxncount() {
		return txncount;
	}

	public void setTxncount(long txncount) {
		this.txncount = txncount;
	}

}
