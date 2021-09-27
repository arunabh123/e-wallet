package com.flip.demo.wallet.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.flip.demo.wallet.models.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

	@Query(nativeQuery = true, value = "select * from TRANSACTION where CREDIT_WALLET_ID = :walletId or DEBIT_WALLET_ID = :walletId order by AMOUNT desc limit 10")
	List<Transaction> topTransactionByWallet(UUID walletId);

}
