package com.edufect.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("accounts-service")
public interface FCAccounts {
	
	@GetMapping(value = "/accounts")
	Object getAccount(Object obj);
	
	@PostMapping(value="/accounts")
	Object postAccount(@RequestBody Object accounts);
	
	@GetMapping("/accounts/{accId}")
	Object getAccountById(@PathVariable("accId") long accId);
	
	@DeleteMapping(value = "/accounts/{accId}")
	Object deleteAccountById(@PathVariable("accId") long accId);
	
	@PutMapping(value = "/accounts/{accId}")
	Object updateAccountById(@PathVariable("accId") long accId, @RequestBody Object accounts);
	
	@GetMapping(value = "/accounts/transactions/{accId}")
	Object getTransactionsByAccId(@PathVariable("accId") long accId,Object obj,@RequestParam("txn")Object obj1);
	
	@PostMapping(value="/accounts/transaction/{accId}")
	Object postTransaction(@RequestBody Object newTransaction,@PathVariable("accId") long accId);
	
	@GetMapping(value = "/accounts/custs")
	Object getCustomerReport(Object obj);
	
	@GetMapping(value = "/accounts/branches")
	Object getBranchReport(Object obj);
}
