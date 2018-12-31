package com.edufect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Id not present")
public class AccountNotFoundException extends RuntimeException {
	 public AccountNotFoundException() {   
	        super(); 
	    }
}
