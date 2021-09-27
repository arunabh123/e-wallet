package com.flip.demo.wallet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flip.demo.wallet.exceptions.InsufficientBalanceException;
import com.flip.demo.wallet.models.User;
import com.flip.demo.wallet.models.Wallet;
import com.flip.demo.wallet.repositories.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Transactional
	public synchronized Wallet addWalletBalance(User user, Double amount) {
		Wallet wallet = user.getWallet();
		amount = wallet.getAmount() + amount;
		wallet.setAmount(amount);
		return walletRepository.save(wallet);
	}

	@Transactional
	public synchronized Wallet deductWalletBalance(User user, Double amount) {
		if (isBalanceSufficient(user, amount)) {
			Wallet wallet = user.getWallet();
			amount = wallet.getAmount() - amount;
			wallet.setAmount(amount);
			return walletRepository.save(wallet);
		} else {
			throw new InsufficientBalanceException();
		}
	}

	public Boolean isBalanceSufficient(User user, Double amount) {
		if (user.getWallet().getAmount() - amount > 0) {
			return true;
		} else {
			return false;
		}
	}

}
