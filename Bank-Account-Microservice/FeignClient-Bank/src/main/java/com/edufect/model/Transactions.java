package com.edufect.model;

public class Transactions {

	private long transId;
	private String type;
	private long typeId;
	private String typeCode;
	private String partyName;
	private long amount;

	private Accounts accounts;

	public long getTransId() {
		return transId;
	}

	public void setTransId(long transId) {
		this.transId = transId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
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

	@Override
	public String toString() {
		return String.format(
				"Transactions [transId=%s, type=%s, typeId=%s, typeCode=%s, partyName=%s, amount=%s, accounts=%s]",
				transId, type, typeId, typeCode, partyName, amount, accounts);
	}

}
