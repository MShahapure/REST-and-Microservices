package com.edufect.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edufect.exception.AccountNotFoundException;
import com.edufect.model.Accounts;
import com.edufect.model.BranchReport;
import com.edufect.model.CustomerReport;
import com.edufect.model.Transactions;
import com.edufect.repository.AccountsRepository;
import com.edufect.repository.TransactionsRepository;

@RestController
@CrossOrigin(allowedHeaders = "*", value = "*")
public class BankAccountsController {

	private static final Logger LOG = LoggerFactory.getLogger(BankAccountsController.class);

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private TransactionsRepository transactionsRepository;

	@GetMapping(value = "/accounts", produces = "application/json")
	public Page<Accounts> getAccounts(Pageable pageable) {
		LOG.info("GET");
		return accountsRepository.findAll(pageable);
	}

	@PostMapping(value = "/accounts", produces = "application/json")
	public ResponseEntity<Accounts> postAccount(@RequestBody Accounts newAccount) {
		LOG.info("POST");
		Accounts accounts = accountsRepository.save(newAccount);
		LOG.debug("Accounts:{}", accounts);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping(value = "/accounts/{accId}", produces = "application/json")
	public Accounts getAccountByAccId(@PathVariable long accId) {
		LOG.info("GET:{} ", accId);
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			LOG.debug("Accounts:{}", accounts);
			return accounts;
		} else {
			throw new AccountNotFoundException();
		}
	}

	@DeleteMapping(value = "/accounts/{accId}")
	public void deleteAccount(@PathVariable long accId) {
		LOG.info("DELETE:{}", accId);
		accountsRepository.deleteById(accId);
	}

	@PutMapping(value = "/accounts/{accId}", produces = "application/json")
	public Accounts updateAccount(@PathVariable long accId, @RequestBody Accounts putAccount) {
		LOG.info("PUT:{} ", accId);
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			accounts.setMobile(putAccount.getMobile());
			accounts.setEmail(putAccount.getEmail());
			accounts.setAccManager(putAccount.getAccManager());
			return accounts;
		} else {
			throw new AccountNotFoundException();
		}
	}

	@GetMapping(value = "/accounts/transactions", produces = "application/json")
	public Page<Transactions> getTransactions(Pageable pageable) {
		LOG.info("GET");
		return transactionsRepository.findAll(pageable);
	}

	@GetMapping(value = "/accounts/transactions/{accId}", produces = "application/json", params = "txn")
	public Page<Transactions> getTransactionsByAccId(@PathVariable long accId, Pageable pageable,
			@RequestParam("txn") String txn) {
		LOG.info("GET:{} ", accId);
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			LOG.debug("Accounts:{}", accounts);
			if (txn.equalsIgnoreCase("Deposit")) {
				Page<Transactions> depositList = transactionsRepository.findByDeposit(accounts, pageable);
				LOG.debug("Deposit List:{}", depositList);
				return depositList;
			} else if (txn.equalsIgnoreCase("Withdraw")) {
				Page<Transactions> withdrawList = transactionsRepository.findByWithdraw(accounts, pageable);
				LOG.debug("Withdraw List:{}", withdrawList);
				return withdrawList;
			} else if (txn.equalsIgnoreCase("All")) {
				Page<Transactions> transactionList = transactionsRepository.findByAccounts(accounts, pageable);
				LOG.debug("Transaction List:{}", transactionList);
				return transactionList;
			}
		} else {
			throw new AccountNotFoundException();
		}
		return null;
	}

	@GetMapping(value = "/accounts/balance/{accId}", produces = "application/json")
	public Long getBalance(@PathVariable long accId) {
		LOG.info("GET:{} ", accId);
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			LOG.debug("Accounts:{}", accounts);
			Optional<Long> currentBalance = transactionsRepository.getCurrentBalance(accounts);
			if (currentBalance.isPresent()) {
				return currentBalance.get();
			}

		} else {
			throw new AccountNotFoundException();
		}
		return null;
	}

	@PostMapping(value = "/accounts/transactions/{accId}", produces = "application/json")
	public ResponseEntity<Transactions> postTransaction(@RequestBody Transactions newTransaction,
			@PathVariable long accId) {
		LOG.info("POST");
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			LOG.debug("Accounts:{}", accounts);
			newTransaction.setAccounts(accounts);
			Transactions transactions = transactionsRepository.save(newTransaction);
			LOG.debug("Transactions:{}", transactions);
			return new ResponseEntity<>(transactions, HttpStatus.OK);
		} else {
			throw new AccountNotFoundException();
		}
	}

	@GetMapping(value = "/accounts/custs", produces = "application/json")
	public Page<CustomerReport> getCustomerReport(Pageable pageable) {
		return transactionsRepository.custRep(pageable);
	}

	@GetMapping(value = "/accounts/branches", produces = "application/json")
	public Page<BranchReport> getBranchReport(Pageable pageable) {
		return transactionsRepository.branchRep(pageable);
	}
}
