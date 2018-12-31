package com.edufect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class NetBankings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long netTransId;
	private String loginName;
	private String partyName;
	private long partyAccNum;
	private String bankName;
	private int bankCode;
	private long amount;

	@ManyToOne
	@JoinColumn(name = "accId")
	@JsonIgnore
	private Accounts accounts2;

	public long getNetTransId() {
		return netTransId;
	}

	public void setNetTransId(long netTransId) {
		this.netTransId = netTransId;
	}

	public Accounts getAccounts2() {
		return accounts2;
	}

	public void setAccounts2(Accounts accounts2) {
		this.accounts2 = accounts2;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

}
