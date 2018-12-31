package com.edufect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edufect.interfaces.FCAccounts;
import com.edufect.interfaces.FCAtms;
import com.edufect.interfaces.FCCheque;
import com.edufect.interfaces.FCNetBanking;

@RestController
@CrossOrigin(value = "*", allowedHeaders = "*")
public class Controller {

	@Autowired
	private FCAccounts fcAccounts;

	@Autowired
	private FCCheque fcCheque;

	@Autowired
	private FCAtms fcAtms;

	@Autowired
	private FCNetBanking fcNetbanking;

	@GetMapping(value = "/accounts")
	public Object getAccount(Object pageable) {
		return fcAccounts.getAccount(pageable);
	}

	@PostMapping(value = "/accounts")
	public Object postAccount(@RequestBody Object accounts) {
		return fcAccounts.postAccount(accounts);
	}

	@GetMapping("/accounts/{accId}")
	public Object getAccountById(@PathVariable("accId") long accId) {
		return fcAccounts.getAccountById(accId);
	}

	@DeleteMapping(value = "/accounts/{accId}")
	public Object deleteAccountById(@PathVariable("accId") long accId) {
		return fcAccounts.deleteAccountById(accId);
	}

	@PutMapping(value = "/accounts/{accId}")
	public Object updateAccountById(@PathVariable("accId") long accId, @RequestBody Object accounts) {
		return fcAccounts.updateAccountById(accId, accounts);
	}

	@GetMapping(value = "/accounts/transactions/{accId}")
	public Object getTransactionsByAccId(@PathVariable("accId") long accId, Object obj,
			@RequestParam("txn") Object obj1) {
		return fcAccounts.getTransactionsByAccId(accId, obj, obj1);
	}

	@PostMapping(value = "/accounts/transaction/{accId}")
	Object postTransaction(@RequestBody Object newTransaction, @PathVariable("accId") long accId) {
		return fcAccounts.postTransaction(newTransaction, accId);
	}

	@GetMapping(value = "/cheques")
	public Object getCheques(Object obj) {
		return fcCheque.getCheques(obj);
	}

	@PostMapping(value = "/cheques/{accId}")
	public Object postCheques(@RequestBody Object newCheques, @PathVariable long accId) {
		return fcCheque.postCheques(newCheques, accId);
	}

	@GetMapping(value = "/atms")
	public Object getAtms(Object obj) {
		return fcAtms.getAtms(obj);
	}

	@PostMapping(value = "/atms/{accId}")
	public Object postAtms(@RequestBody Object newAtms, @PathVariable long accId) {
		return fcAtms.postAtms(newAtms, accId);
	}

	@GetMapping(value = "/netbankings")
	public Object getNetBanking(Object obj) {
		return fcNetbanking.getNetBanking(obj);
	}

	@PostMapping(value = "/netbankings/{accId}")
	public Object postNetBanking(@RequestBody Object newNetBanking, @PathVariable long accId) {
		return fcNetbanking.postNetBanking(newNetBanking, accId);
	}

	@GetMapping(value = "/accounts/custs")
	Object getCustomerReport(Object obj) {
		return fcAccounts.getCustomerReport(obj);
	}

	@GetMapping(value = "/accounts/branches")
	Object getBranchReport(Object obj) {
		return fcAccounts.getBranchReport(obj);
	}
}
