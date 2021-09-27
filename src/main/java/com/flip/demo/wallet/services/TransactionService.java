package com.flip.demo.wallet.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flip.demo.wallet.exceptions.InsufficientBalanceException;
import com.flip.demo.wallet.models.AggregatedWalletTransfer;
import com.flip.demo.wallet.models.Transaction;
import com.flip.demo.wallet.models.User;
import com.flip.demo.wallet.repositories.AggregatedWalletTransferRepository;
import com.flip.demo.wallet.repositories.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private AggregatedWalletTransferRepository aggregatedWalletTransferRepository;

	@Transactional
	public synchronized Transaction transferAmount(User fromUser, User toUser, Double amount) {
		if (walletService.isBalanceSufficient(fromUser, amount)) {
			Transaction txn = this.createTransaction(fromUser, toUser, amount);
			walletService.deductWalletBalance(fromUser, amount);
			walletService.addWalletBalance(toUser, amount);
			AggregatedWalletTransfer aggregatedTransfer = aggregatedWalletTransferRepository.findAggregatedWalletTransferByWalletId(fromUser.getWallet().getId());
			if(Objects.isNull(aggregatedTransfer)) {
				aggregatedTransfer = new AggregatedWalletTransfer();
				aggregatedTransfer.setAmount(0.0);
				aggregatedTransfer.setWallet(fromUser.getWallet());
			}
			aggregatedTransfer.setAmount(aggregatedTransfer.getAmount() + amount);
			aggregatedWalletTransferRepository.save(aggregatedTransfer);
			return txn;
		} else {
			throw new InsufficientBalanceException();
		}
	}

	private synchronized Transaction createTransaction(User fromUser, User toUser, Double amount) {
		Transaction txn = new Transaction();
		txn.setDebitedWallet(fromUser.getWallet());
		txn.setCreditedWallet(toUser.getWallet());
		txn.setAmount(amount);
		return transactionRepository.save(txn);
	}

}
