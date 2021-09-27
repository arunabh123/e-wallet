package com.flip.demo.wallet.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.flip.demo.wallet.models.User;
import com.flip.demo.wallet.models.WalletBalanceRequest;
import com.flip.demo.wallet.models.WalletBalanceResponse;
import com.flip.demo.wallet.services.UserService;
import com.flip.demo.wallet.services.WalletService;
import com.sun.istack.NotNull;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;

	@PostMapping("/balance_topup")
	public ResponseEntity<Void> updateWalletAmount(@Valid @RequestBody WalletBalanceRequest walletBalanceReq,@NotNull @RequestHeader(name="Authorization") UUID userId) {
		User user = userService.authenticateAndGetUser(userId);
		walletService.addWalletBalance(user, walletBalanceReq.getAmount());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/balance_read")
	public ResponseEntity<WalletBalanceResponse> getWalletAmount(@NotNull @RequestHeader(name="Authorization") UUID userId) {
		User user = userService.authenticateAndGetUser(userId);
		return ResponseEntity.ok(new WalletBalanceResponse(user.getWallet().getAmount()));
	}
	
}
