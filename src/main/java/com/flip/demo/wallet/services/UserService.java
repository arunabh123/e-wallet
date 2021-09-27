package com.flip.demo.wallet.services;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flip.demo.wallet.exceptions.UserAlreadyExistsException;
import com.flip.demo.wallet.exceptions.UserNotAuthenticatedException;
import com.flip.demo.wallet.exceptions.UserNotFoundException;
import com.flip.demo.wallet.models.User;
import com.flip.demo.wallet.models.Wallet;
import com.flip.demo.wallet.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		if (Objects.isNull(userRepository.findUserByUsername(user.getUsername()))) {
			Wallet wallet = new Wallet();
			user.setWallet(wallet);
			return userRepository.save(user);
		} else {
			throw new UserAlreadyExistsException();
		}
	}
	
	public User getUserById(UUID userId) {
		Optional<User> user = userRepository.findById(userId);
		return user.orElseThrow(UserNotFoundException::new);
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}
	
	public User authenticateAndGetUser(UUID userId) {
		try {
		return getUserById(userId);
		} catch(UserNotFoundException e) {
			throw new UserNotAuthenticatedException();
		}
	}
}
