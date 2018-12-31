package com.edufect.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Accounts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accId;
	private int custCode;
	private String name;
	private String email;
	private String mobile;
	private int branchCode;
	private String accManager;

	@OneToMany(mappedBy = "accounts")
	@JsonIgnore
	private Set<Atms> atms;

	@OneToMany(mappedBy = "accounts1")
	@JsonIgnore
	private Set<Cheques> cheques;

	@OneToMany(mappedBy = "accounts2")
	@JsonIgnore
	private Set<NetBankings> netBankings;

	@OneToMany(mappedBy = "accounts3")
	@JsonIgnore
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

}
