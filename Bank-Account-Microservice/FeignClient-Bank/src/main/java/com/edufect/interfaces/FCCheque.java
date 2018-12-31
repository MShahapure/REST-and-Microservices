package com.edufect.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("cheques-service")
public interface FCCheque {

	@GetMapping(value = "/cheques")
	Object getCheques(Object obj);
	
	@PostMapping(value = "/cheques/{accId}")
	Object postCheques(@RequestBody Object newCheques, @PathVariable long accId);
}
