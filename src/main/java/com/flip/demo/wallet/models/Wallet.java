package com.flip.demo.wallet.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="WALLET")
public class Wallet {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	        name = "UUID",
	        strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "ID", updatable = false, nullable = false)
	@ColumnDefault("random_uuid()")
	private UUID id;
	
	@OneToOne(mappedBy="wallet" , cascade=CascadeType.ALL)
	private User user;
	
	@Column(name="BALANCE" , nullable = false)
	@ColumnDefault("0.0")
	private Double amount = 0.0;
	
	@Column(name="CREATED_DATE" , nullable = false, updatable=false)
	@CreationTimestamp
	private Date createdDate;
	
	@OneToOne(mappedBy="wallet" , cascade = CascadeType.ALL)
	private AggregatedWalletTransfer aggregatedWalletTransfer;
	
}
