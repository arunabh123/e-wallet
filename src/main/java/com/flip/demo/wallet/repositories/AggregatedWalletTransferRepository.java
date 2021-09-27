package com.flip.demo.wallet.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flip.demo.wallet.models.AggregatedWalletTransfer;

@Repository
public interface AggregatedWalletTransferRepository extends CrudRepository<AggregatedWalletTransfer, UUID> {

	@Query(nativeQuery = true, value = "select * from AGGREGATED_WALLET_TRANSFER order by AMOUNT desc limit 10")
	List<AggregatedWalletTransfer> getTopWalletTransfers();
	
	@Query(nativeQuery = true, value = "select * from AGGREGATED_WALLET_TRANSFER where WALLET_ID=:wallet_id")
	AggregatedWalletTransfer findAggregatedWalletTransferByWalletId(@Param("wallet_id") UUID walletId);
}
