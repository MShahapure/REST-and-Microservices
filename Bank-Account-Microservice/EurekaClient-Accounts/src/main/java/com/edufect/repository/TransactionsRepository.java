package com.edufect.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edufect.model.Accounts;
import com.edufect.model.BranchReport;
import com.edufect.model.CustomerReport;
import com.edufect.model.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

	Page<Transactions> findByAccounts(Accounts accounts, Pageable pageable);

	@Query("SELECT t FROM Transactions t WHERE t.accounts =:accounts AND t.amount > 0")
	Page<Transactions> findByDeposit(Accounts accounts, Pageable pageable);

	@Query("SELECT t FROM Transactions t WHERE t.accounts =:accounts AND t.amount < 0")
	Page<Transactions> findByWithdraw(Accounts accounts, Pageable pageable);

	@Query("SELECT new com.edufect.model.CustomerReport (a.accId,a.custCode, COUNT(t.transId) ,SUM(t.amount))"
			+ " FROM Accounts a JOIN a.transactions t GROUP BY a.accId")
	Page<CustomerReport> custRep(Pageable pageable);

	@Query("SELECT new com.edufect.model.BranchReport (a.branchCode,COUNT(a.branchCode),SUM(t.amount),COUNT(t.transId))"
			+ " FROM Accounts a JOIN a.transactions t GROUP BY a.branchCode")
	Page<BranchReport> branchRep(Pageable pageable);

//	@Query("SELECT COUNT(t.transId) FROM Transactions t WHERE t.accounts3 =:accounts3")
//	Optional<Integer> countByTransactions(@Param("accounts3") Accounts accounts);
//	
	@Query("SELECT SUM(t.amount) FROM Transactions t WHERE t.accounts =:accounts")
	Optional<Long> getCurrentBalance(@Param("accounts") Accounts accounts);

}
