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
import com.edufect.model.Cheques;
import com.edufect.model.Transactions;
import com.edufect.repository.ChequesRepository;

@RestController
@CrossOrigin(allowedHeaders = "*", value = "*")
public class BankAccountsController {

	private static final Logger LOG = LoggerFactory.getLogger(BankAccountsController.class);

	@Autowired
	private ChequesRepository chequesRepository;

	@GetMapping(value = "/cheques", produces = "application/json")
	public Page<Cheques> getCheques(Pageable pageable) {
		LOG.info("GET");
		return chequesRepository.findAll(pageable);
	}

	@PostMapping(value = "/cheques/{accId}", produces = "application/json")
	public ResponseEntity<Cheques> postCheques(@RequestBody Cheques newCheques, @PathVariable long accId) {
		LOG.info("POST");

		RestTemplate restTemplate = new RestTemplate();

		String urlToGetAccount = "http://localhost:2221/accounts/" + accId;
		Accounts account = restTemplate.getForObject(urlToGetAccount, Accounts.class);
		if (account != null) {

			String urlToGetBalance = "http://localhost:2221/accounts/balance/" + accId;
			long currentBalance = restTemplate.getForObject(urlToGetBalance, Long.class);

			if ((Math.abs(newCheques.getAmount()) <= currentBalance || newCheques.getAmount() > 0)) {
				newCheques.setAccId(accId);
				Cheques cheques = chequesRepository.save(newCheques);
				Transactions transactions = new Transactions();

				transactions.setAccounts(account);
				transactions.setAmount(cheques.getAmount());
				transactions.setPartyName(cheques.getPartyName());
				transactions.setType("By Cheque");
				transactions.setTypeCode(cheques.getChqNumber());
				transactions.setTypeId(cheques.getChqTransId());
				String urlToPostTransaction = "http://localhost:2221/accounts/transactions/" + accId;
				Transactions transactionResult = restTemplate.postForObject(urlToPostTransaction, transactions,
						Transactions.class);
				LOG.debug("Transactions:{}", transactions);
				LOG.debug("Transaction Result:{}", transactionResult);
				LOG.debug("Cheques:{}", cheques);
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
		LOG.info("GET:{} ", accId);
		RestTemplate restTemplate = new RestTemplate();
		String urlToGetAccount = "http://localhost:2221/accounts/" + accId;
		Accounts account = restTemplate.getForObject(urlToGetAccount, Accounts.class);
		if (account != null) {
			Page<Cheques> chequesList = chequesRepository.findByAccId(accId, pageable);
			LOG.debug("Cheques List:{}", chequesList);
			return chequesList;
		} else {
			throw new AccountNotFoundException();
		}
	}

}
