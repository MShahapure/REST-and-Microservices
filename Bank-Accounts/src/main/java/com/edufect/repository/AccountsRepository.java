package com.edufect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edufect.model.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Long>{

}
