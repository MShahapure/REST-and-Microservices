package com.edufect.model;

import java.util.Set;

public class Accounts {
	private long accId;
	private int custCode;
	private String name;
	private String email;
	private String mobile;
	private int branchCode;
	private String accManager;

	private Set<Transactions> transactions;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}

	public String getAccManager() {
		return accManager;
	}

	public void setAccManager(String accManager) {
		this.accManager = accManager;
	}

	public Set<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transactions> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return String.format(
				"Accounts [accId=%s, custCode=%s, name=%s, email=%s, mobile=%s, branchCode=%s, accManager=%s, transactions=%s]",
				accId, custCode, name, email, mobile, branchCode, accManager, transactions);
	}

}
