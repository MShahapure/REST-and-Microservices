package com.edufect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.edufect.exception.AccountNotFoundException;
import com.edufect.exception.ForbiddenException;
import com.edufect.model.Accounts;
import com.edufect.model.Atms;
import com.edufect.model.Transactions;
import com.edufect.repository.AtmsRepository;

@RestController
@CrossOrigin(allowedHeaders = "*", value = "*")
public class BankAccountsController {

	private static final Logger LOG = LoggerFactory.getLogger(BankAccountsController.class);

	@Autowired
	private AtmsRepository atmsRepository;

	@GetMapping(value = "/atms", produces = "application/json")
	public Page<Atms> getAtms(Pageable pageable) {
		LOG.info("GET");
		return atmsRepository.findAll(pageable);
	}

	@PostMapping(value = "/atms/{accId}", produces = "application/json")
	public ResponseEntity<Atms> postAtms(@RequestBody Atms newAtms, @PathVariable long accId) {
		LOG.info("POST");
		RestTemplate restTemplate = new RestTemplate();

		String urlToGetAccount = "http://localhost:2221/accounts/" + accId;
		Accounts account = restTemplate.getForObject(urlToGetAccount, Accounts.class);
		if (account != null) {

			String urlToGetBalance = "http://localhost:2221/accounts/balance/" + accId;
			long currentBalance = restTemplate.getForObject(urlToGetBalance, Long.class);
			if ((Math.abs(newAtms.getAmount()) <= currentBalance || newAtms.getAmount() > 0)) {
				newAtms.setAccId(accId);
				Atms atms = atmsRepository.save(newAtms);
				Transactions transactions = new Transactions();
				transactions.setAccounts(account);
				transactions.setAmount(atms.getAmount());
				transactions.setPartyName("Self");
				transactions.setType("By ATM");
				transactions.setTypeCode(atms.getAtmNumber());
				transactions.setTypeId(atms.getAtmTransId());
				String urlToPostTransaction = "http://localhost:2221/accounts/transactions/" + accId;
				Transactions transactionResult = restTemplate.postForObject(urlToPostTransaction, transactions,
						Transactions.class);
				LOG.debug("Transaction:{}", transactions);
				LOG.debug("Transaction Result:{}", transactionResult);
				LOG.debug("ATMs:{}", atms);
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
		LOG.info("GET:{} ", accId);
		RestTemplate restTemplate = new RestTemplate();
		String urlToGetAccount = "http://localhost:2221/accounts/" + accId;
		Accounts account = restTemplate.getForObject(urlToGetAccount, Accounts.class);
		if (account != null) {
			Page<Atms> atmsList = atmsRepository.findByAccId(accId, pageable);
			LOG.debug("ATM List:{}", atmsList);
			return atmsList;
		} else {
			throw new AccountNotFoundException();
		}
	}

}
