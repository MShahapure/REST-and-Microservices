package com.edufect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edufect.model.Accounts;
import com.edufect.model.Cheques;

public interface ChequesRepository extends JpaRepository<Cheques, Long> {

	Page<Cheques> findByAccounts1(Accounts accounts1, Pageable pageable);
}
