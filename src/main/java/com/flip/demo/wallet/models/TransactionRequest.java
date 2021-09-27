package com.flip.demo.wallet.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class TransactionRequest {

	@JsonProperty("to_username")
	private String toUsername;
	
	@NotNull
	@Min(value=0)
	private Double amount;
}
