package com.edufect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Atms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long atmTransId;
	private String atmNumber;
	private long amount;
	private long accId;

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

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	@Override
	public String toString() {
		return String.format("Atms [atmTransId=%s, atmNumber=%s, amount=%s, accId=%s]", atmTransId, atmNumber, amount,
				accId);
	}

}
