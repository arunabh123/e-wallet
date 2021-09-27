package com.flip.demo.wallet.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TRANSACTION")
public class Transaction {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	        name = "UUID",
	        strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "ID", updatable = false, nullable = false)
	@ColumnDefault("random_uuid()")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "DEBIT_WALLET_ID")
	private Wallet debitedWallet;
	
	@ManyToOne
	@JoinColumn(name = "CREDIT_WALLET_ID")
	private Wallet creditedWallet;
	
	@Column(name = "AMOUNT", updatable = false, nullable = false)
	private Double amount;
	
	@Column(name = "DATE", updatable = false, nullable = false)
	@CreationTimestamp
	private Date date;
}
