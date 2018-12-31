package com.edufect.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("netbanking-service")
public interface FCNetBanking {

	@GetMapping(value = "/netbankings")
	Object getNetBanking(Object obj);

	@PostMapping(value = "/netbankings/{accId}")
	Object postNetBanking(@RequestBody Object newNetBanking, @PathVariable long accId);
}
