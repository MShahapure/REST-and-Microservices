package com.edufect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Atms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long atmTransId;
	private String atmNumber;
	private long amount;

	@ManyToOne
	@JoinColumn(name = "accId")
//	@JsonIgnore
	private Accounts accounts;

	public long getAtmTransId() {
		return atmTransId;
	}

	public void setAtmTransId(long atmTransId) {
		this.atmTransId = atmTransId;
	}

	public String getAtmNumber() {
		return atmNumber;
	}

	public void setAtmNumber(String atmNumber) {
		this.atmNumber = atmNumber;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Accounts getAccounts() {
		return accounts;
	}

	public void setAccounts(Accounts accounts) {
		this.accounts = accounts;
	}

}
