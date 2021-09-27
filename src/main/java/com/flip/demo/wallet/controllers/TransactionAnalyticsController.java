package com.flip.demo.wallet.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.flip.demo.wallet.models.OverallTopTransactionResponse;
import com.flip.demo.wallet.models.TopTransactionsResponse;
import com.flip.demo.wallet.models.User;
import com.flip.demo.wallet.services.TransactionAnalyticsService;
import com.flip.demo.wallet.services.UserService;
import com.sun.istack.NotNull;

@RestController
public class TransactionAnalyticsController {

	@Autowired
	private TransactionAnalyticsService transactionAnalyticsService;

	@Autowired
	private UserService userService;

	@GetMapping("/top_transactions_per_user")
	public ResponseEntity<List<TopTransactionsResponse>> getUserTopTransactions(
			@NotNull @RequestHeader(name = "Authorization") UUID userId) {
		User user = userService.authenticateAndGetUser(userId);
		List<TopTransactionsResponse> topTransactions = transactionAnalyticsService.getTopTransactionsByUsers(user);
		return ResponseEntity.ok(topTransactions);
	}

	@GetMapping("/top_users")
	public ResponseEntity<List<OverallTopTransactionResponse>> getOverallTopTransactingUsers(
			@NotNull @RequestHeader(name = "Authorization") UUID userId) {
		userService.authenticateAndGetUser(userId);
		List<OverallTopTransactionResponse> topTransactions = transactionAnalyticsService.getOverallTopTransactions();
		return ResponseEntity.ok(topTransactions);
	}
}
