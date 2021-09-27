package com.flip.demo.wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flip.demo.wallet.models.User;
import com.flip.demo.wallet.models.UserCreationResponse;
import com.flip.demo.wallet.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping(value = "/create_user")
	public ResponseEntity<UserCreationResponse> registerUser(@RequestBody User user) {
		user = userService.createUser(user);
		return ResponseEntity.ok(new UserCreationResponse(user.getId()));
	}
}
