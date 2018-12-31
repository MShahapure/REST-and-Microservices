package com.edufect.controller;

import java.util.Optional;

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
import com.edufect.exception.ForbiddenException;
import com.edufect.model.Accounts;
import com.edufect.model.Atms;
import com.edufect.model.BranchReport;
import com.edufect.model.Cheques;
import com.edufect.model.CustomerReport;
import com.edufect.model.NetBankings;
import com.edufect.model.Transactions;
import com.edufect.repository.AccountsRepository;
import com.edufect.repository.AtmsRepository;
import com.edufect.repository.ChequesRepository;
import com.edufect.repository.NetBankingsRepository;
import com.edufect.repository.TransactionsRepository;

@RestController
@CrossOrigin(allowedHeaders = "*", value = "*")
public class BankAccountsController {

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private AtmsRepository atmsRepository;

	@Autowired
	private ChequesRepository chequesRepository;

	@Autowired
	private NetBankingsRepository netBankingsRepository;

	@Autowired
	private TransactionsRepository transactionsRepository;

	@GetMapping(value = "/accounts", produces = "application/json")
	public Page<Accounts> getAccounts(Pageable pageable) {
		System.out.println("GET");
		return accountsRepository.findAll(pageable);
	}

	@PostMapping(value = "/accounts", produces = "application/json")
	public ResponseEntity<Accounts> postAccount(@RequestBody Accounts newAccount) {
		System.out.println("POST");
		Accounts accounts = accountsRepository.save(newAccount);
		System.out.println(accounts);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping(value = "/accounts/{accId}", produces = "application/json")
	public Accounts getAccountByAccId(@PathVariable long accId) {
		System.out.println("GET:: " + accId);
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			System.out.println(accounts);
			return accounts;
		} else {
			throw new AccountNotFoundException();
		}
	}

	@DeleteMapping(value = "/accounts/{accId}")
	public void deleteAccount(@PathVariable long accId) {
		System.out.println("DELETE:: " + accId);
		accountsRepository.deleteById(accId);
	}

	@PutMapping(value = "/accounts/{accId}", produces = "application/json")
	public Accounts updateAccount(@PathVariable long accId, @RequestBody Accounts putAccount) {
		System.out.println("PUT:: " + accId);
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
		System.out.println("GET");
		return transactionsRepository.findAll(pageable);
	}

	@GetMapping(value = "/accounts/transactions/{accId}", produces = "application/json", params = "txn")
	public Page<Transactions> getTransactionsByAccId(@PathVariable long accId, Pageable pageable,
			@RequestParam("txn") String txn) {
		System.out.println("GET:: " + accId);
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			System.out.println(accounts);
			if (txn.equalsIgnoreCase("Deposit")) {
				Page<Transactions> depositList = transactionsRepository.findByDeposit(accounts, pageable);
				System.out.println(depositList);
				return depositList;
			} else if (txn.equalsIgnoreCase("Withdraw")) {
				Page<Transactions> withdrawList = transactionsRepository.findByWithdraw(accounts, pageable);
				System.out.println(withdrawList);
				return withdrawList;
			} else if (txn.equalsIgnoreCase("All")) {
				Page<Transactions> transactionList = transactionsRepository.findByAccounts3(accounts, pageable);
				System.out.println(transactionList);
				return transactionList;
			}
		} else {
			throw new AccountNotFoundException();
		}
		return null;
	}

	@GetMapping(value = "/cheques", produces = "application/json")
	public Page<Cheques> getCheques(Pageable pageable) {
		System.out.println("GET");
		return chequesRepository.findAll(pageable);
	}

	@PostMapping(value = "/cheques/{accId}", produces = "application/json")
	public ResponseEntity<Cheques> postCheques(@RequestBody Cheques newCheques, @PathVariable long accId) {
		System.out.println("POST");
		Optional<Accounts> accountsList = accountsRepository.findById(accId);

		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			newCheques.setAccounts1(accounts);

			Optional<Long> currentBalance = transactionsRepository.getCurrentBalance(accounts);

			if (Math.abs(newCheques.getAmount()) <= currentBalance.get() || newCheques.getAmount() > 0) {

				Cheques cheques = chequesRepository.save(newCheques);
				Transactions transactions = new Transactions();
				transactions.setAccounts3(cheques.getAccounts1());
				transactions.setAmount(cheques.getAmount());
				transactions.setPartyName(cheques.getPartyName());
				transactions.setType("By Cheque");
				transactions.setTypeCode(cheques.getChqNumber());
				transactions.setTypeId(cheques.getChqTransId());
				transactionsRepository.save(transactions);
				System.out.println(transactions);
				System.out.println(cheques);
				return new ResponseEntity<>(cheques, HttpStatus.OK);
			} else {

				throw new ForbiddenException();
			}
		} else {
			throw new AccountNotFoundException();
		}
	}

	@GetMapping(value = "/cheques/{accId}", produces = "application/json")
	public Page<Cheques> getChequesByAccId(@PathVariable long accId, Pageable pageable) {
		System.out.println("GET:: " + accId);
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {

			Accounts accounts = accountsList.get();

			Page<Cheques> chequeList = chequesRepository.findByAccounts1(accounts, pageable);
			System.out.println(chequeList);
			return chequeList;
		} else {
			throw new AccountNotFoundException();
		}
	}

	@GetMapping(value = "/atms", produces = "application/json")
	public Page<Atms> getAtms(Pageable pageable) {
		System.out.println("GET");
		return atmsRepository.findAll(pageable);
	}

	@PostMapping(value = "/atms/{accId}", produces = "application/json")
	public ResponseEntity<Atms> postAtms(@RequestBody Atms newAtms, @PathVariable long accId) {
		System.out.println("POST");
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			newAtms.setAccounts(accounts);

			Optional<Long> currentBalance = transactionsRepository.getCurrentBalance(accounts);

			if (Math.abs(newAtms.getAmount()) <= currentBalance.get() || newAtms.getAmount() > 0) {
				Atms atms = atmsRepository.save(newAtms);
				Transactions transactions = new Transactions();
				transactions.setAccounts3(atms.getAccounts());
				transactions.setAmount(atms.getAmount());
				transactions.setPartyName("Self");
				transactions.setType("By ATM");
				transactions.setTypeCode(atms.getAtmNumber());
				transactions.setTypeId(atms.getAtmTransId());
				transactionsRepository.save(transactions);
				System.out.println(transactions);
				System.out.println(atms);
				return new ResponseEntity<>(atms, HttpStatus.OK);
			} else {

				throw new ForbiddenException();
			}
		} else {
			throw new AccountNotFoundException();
		}
	}

	@GetMapping(value = "/atms/{accId}", produces = "application/json")
	public Page<Atms> getAtmsByAccId(@PathVariable long accId, Pageable pageable) {
		System.out.println("GET:: " + accId);
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();

			Page<Atms> atmsList = atmsRepository.findByAccounts(accounts, pageable);
			System.out.println(atmsList);
			return atmsList;
		} else {
			throw new AccountNotFoundException();
		}
	}

	@GetMapping(value = "/netbankings", produces = "application/json")
	public Page<NetBankings> getNetbankings(Pageable pageable) {
		System.out.println("GET");
		return netBankingsRepository.findAll(pageable);
	}

	@PostMapping(value = "/netbankings/{accId}", produces = "application/json")
	public ResponseEntity<NetBankings> postNetBanking(@RequestBody NetBankings newNetBankings,
			@PathVariable long accId) {
		System.out.println("POST");
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();
			newNetBankings.setAccounts2(accounts);
			Optional<Long> currentBalance = transactionsRepository.getCurrentBalance(accounts);

			if (Math.abs(newNetBankings.getAmount()) <= currentBalance.get() || newNetBankings.getAmount() > 0) {
				NetBankings netBankings = netBankingsRepository.save(newNetBankings);
				Transactions transactions = new Transactions();
				transactions.setAccounts3(netBankings.getAccounts2());
				transactions.setAmount(netBankings.getAmount());
				transactions.setPartyName(netBankings.getPartyName());
				transactions.setType("By NetBanking");
				transactions.setTypeCode(netBankings.getLoginName());
				transactions.setTypeId(netBankings.getNetTransId());
				transactionsRepository.save(transactions);
				System.out.println(transactions);
				System.out.println(netBankings);
				return new ResponseEntity<>(netBankings, HttpStatus.OK);
			} else {

				throw new ForbiddenException();
			}
		} else {
			throw new AccountNotFoundException();
		}
	}

	@GetMapping(value = "/netbankings/{accId}", produces = "application/json")
	public Page<NetBankings> getNetBankingByAccId(@PathVariable long accId, Pageable pageable) {
		System.out.println("GET:: " + accId);
		Optional<Accounts> accountsList = accountsRepository.findById(accId);
		if (accountsList.isPresent()) {
			Accounts accounts = accountsList.get();

			Page<NetBankings> netBankingList = netBankingsRepository.findByAccounts2(accounts, pageable);
			System.out.println(netBankingList);
			return netBankingList;
		} else {
			throw new AccountNotFoundException();
		}
	}

//	@GetMapping(value = "/accounts/custs", produces = "application/json")
//	public List<CustomerReport> getCustomerReport(){
//		List<Accounts> accountList=accountsRepository.findAll();
//		List<CustomerReport> custReport=new ArrayList<>();
//		for(Accounts accounts :accountList) {
//			CustomerReport report=new CustomerReport();
//			report.setAccId(accounts.getAccId());
//			report.setCustCode(accounts.getCustCode());
//			Optional<Integer> countByTransactions = transactionsRepository.countByTransactions(accounts);
//			Optional<Long> currentBalance = transactionsRepository.getCurrentBalance(accounts);
//			report.setCurrentBalance(currentBalance.get());
//			report.setTotalTransactions(countByTransactions.get());
//			custReport.add(report);
//		}
//		return custReport;
//	}

	@GetMapping(value = "/accounts/custs", produces = "application/json")
	public Page<CustomerReport> getCustomerReport(Pageable pageable) {
		return transactionsRepository.custRep(pageable);
	}

	@GetMapping(value = "/accounts/branches", produces = "application/json")
	public Page<BranchReport> getBranchReport(Pageable pageable) {
		return transactionsRepository.branchRep(pageable);
	}
}
