package com.flip.demo.wallet.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverallTopTransactionResponse {

	private String username;

	@JsonProperty("transacted_value")
	private Double amount;
}
