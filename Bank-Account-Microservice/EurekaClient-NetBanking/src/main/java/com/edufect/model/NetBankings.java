package com.edufect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	private long accId;

	public long getNetTransId() {
		return netTransId;
	}

	public void setNetTransId(long netTransId) {
		this.netTransId = netTransId;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
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

	@Override
	public String toString() {
		return String.format(
				"NetBankings [netTransId=%s, loginName=%s, partyName=%s, partyAccNum=%s, bankName=%s, bankCode=%s, amount=%s, accId=%s]",
				netTransId, loginName, partyName, partyAccNum, bankName, bankCode, amount, accId);
	}

}
