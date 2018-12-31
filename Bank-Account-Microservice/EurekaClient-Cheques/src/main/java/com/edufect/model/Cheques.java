package com.edufect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private long accId;

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

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	@Override
	public String toString() {
		return String.format(
				"Cheques [chqTransId=%s, chqNumber=%s, partyName=%s, partyAccNum=%s, bankName=%s, bankCode=%s, amount=%s, accId=%s]",
				chqTransId, chqNumber, partyName, partyAccNum, bankName, bankCode, amount, accId);
	}

}
