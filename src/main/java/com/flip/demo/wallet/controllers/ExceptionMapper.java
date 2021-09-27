package com.flip.demo.wallet.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flip.demo.wallet.exceptions.InsufficientBalanceException;
import com.flip.demo.wallet.exceptions.UserAlreadyExistsException;
import com.flip.demo.wallet.exceptions.UserNotAuthenticatedException;
import com.flip.demo.wallet.exceptions.UserNotFoundException;

@ControllerAdvice
public class ExceptionMapper extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNotAuthenticatedException.class)
	public ResponseEntity<Object> handleUserNotAuthenticatedException(UserNotAuthenticatedException e,
			WebRequest request) {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<Object> handleInsufficientBalanceException(InsufficientBalanceException e,
			WebRequest request) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException e, WebRequest request) {
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
