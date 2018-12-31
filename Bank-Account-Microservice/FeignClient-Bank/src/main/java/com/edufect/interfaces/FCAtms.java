package com.edufect.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("atm-service")
public interface FCAtms {

	@GetMapping(value = "/atms")
	Object getAtms(Object obj);
	
	@PostMapping(value = "/atms/{accId}")
	Object postAtms(@RequestBody Object newAtm, @PathVariable long accId);
}
