package com.flip.demo.wallet.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flip.demo.wallet.models.AggregatedWalletTransfer;
import com.flip.demo.wallet.models.OverallTopTransactionResponse;
import com.flip.demo.wallet.models.TopTransactionsResponse;
import com.flip.demo.wallet.models.Transaction;
import com.flip.demo.wallet.models.User;
import com.flip.demo.wallet.repositories.AggregatedWalletTransferRepository;
import com.flip.demo.wallet.repositories.TransactionRepository;

@Service
public class TransactionAnalyticsService {

	@Autowired
	private AggregatedWalletTransferRepository aggregatedWalletTransferRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	public List<OverallTopTransactionResponse> getOverallTopTransactions() {
		List<AggregatedWalletTransfer> transfers = aggregatedWalletTransferRepository.getTopWalletTransfers();
		return transfers.stream().map(transfer-> {
			OverallTopTransactionResponse topTransactionsResponse = new OverallTopTransactionResponse();
			topTransactionsResponse.setUsername(transfer.getWallet().getUser().getUsername());
			topTransactionsResponse.setAmount(transfer.getAmount());
			return topTransactionsResponse;
		}).collect(Collectors.toList());
	}
	
	public List<TopTransactionsResponse> getTopTransactionsByUsers(User user) {
		List<Transaction> txns = transactionRepository.topTransactionByWallet(user.getWallet().getId());
		return txns.stream().map(txn -> {
			TopTransactionsResponse topTransactionsResponse = new TopTransactionsResponse();
			
			if(txn.getCreditedWallet().equals(user.getWallet())) {
				topTransactionsResponse.setAmount(txn.getAmount());
				topTransactionsResponse.setUsername(txn.getDebitedWallet().getUser().getUsername());
			} else {
				topTransactionsResponse.setAmount(-txn.getAmount());
				topTransactionsResponse.setUsername(txn.getCreditedWallet().getUser().getUsername());
			}
			return topTransactionsResponse;
		}).collect(Collectors.toList());
	}
}
