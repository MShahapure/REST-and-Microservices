package com.edufect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cheques {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long chqTransId;
	private String chqNumber;
	private String partyName;
	private long partyAccNum;
	private String bankName;
	private int bankCode;
	private long amount;

	@ManyToOne
	@JoinColumn(name = "accId")
	@JsonIgnore
	private Accounts accounts1;

	public long getChqTransId() {
		return chqTransId;
	}

	public void setChqTransId(long chqTransId) {
		this.chqTransId = chqTransId;
	}

	public String getChqNumber() {
		return chqNumber;
	}

	public void setChqNumber(String chqNumber) {
		this.chqNumber = chqNumber;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public long getPartyAccNum() {
		return partyAccNum;
	}

	public void setPartyAccNum(long partyAccNum) {
		this.partyAccNum = partyAccNum;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getBankCode() {
		return bankCode;
	}

	public void setBankCode(int bankCode) {
		this.bankCode = bankCode;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Accounts getAccounts1() {
		return accounts1;
	}

	public void setAccounts1(Accounts accounts1) {
		this.accounts1 = accounts1;
	}

}
