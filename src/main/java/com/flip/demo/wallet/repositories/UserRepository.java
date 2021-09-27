package com.flip.demo.wallet.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flip.demo.wallet.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

	User findUserByUsername(String username);
}
