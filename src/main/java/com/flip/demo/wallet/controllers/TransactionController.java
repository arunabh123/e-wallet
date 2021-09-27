package com.flip.demo.wallet.controllers;

import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.flip.demo.wallet.exceptions.UserNotFoundException;
import com.flip.demo.wallet.models.Transaction;
import com.flip.demo.wallet.models.TransactionRequest;
import com.flip.demo.wallet.models.User;
import com.flip.demo.wallet.services.TransactionService;
import com.flip.demo.wallet.services.UserService;
import com.sun.istack.NotNull;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;

	@PostMapping("/transfer")
	public ResponseEntity<Void> transferAmount(@Valid @RequestBody TransactionRequest transactionRequest,
			@NotNull @RequestHeader(name = "Authorization") UUID userId) {
		User fromUser = userService.authenticateAndGetUser(userId);
		User toUser = userService.getUserByUsername(transactionRequest.getToUsername());
		if(Objects.isNull(toUser)) throw new UserNotFoundException();
		Transaction txn = transactionService.transferAmount(fromUser, toUser, transactionRequest.getAmount());
		return ResponseEntity.noContent().build();
	}

}
