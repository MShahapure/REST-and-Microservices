package com.edufect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Insufficient balance")
public class ForbiddenException extends RuntimeException {
	public ForbiddenException() {
		super();
	}
}
