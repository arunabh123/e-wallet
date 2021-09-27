package com.flip.demo.wallet.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletBalanceRequest {

	@NotNull
	@Max(value=10000000)
	@Min(value=0)
	private Double amount;
}
