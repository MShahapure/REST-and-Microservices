package com.edufect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edufect.model.Atms;

public interface AtmsRepository extends JpaRepository<Atms, Long> {

	Page<Atms> findByAccId(long accId,Pageable pageable);
}
