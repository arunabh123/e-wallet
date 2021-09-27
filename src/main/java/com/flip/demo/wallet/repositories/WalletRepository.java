package com.flip.demo.wallet.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.flip.demo.wallet.models.Wallet;

public interface WalletRepository extends CrudRepository<Wallet, UUID>{

}
