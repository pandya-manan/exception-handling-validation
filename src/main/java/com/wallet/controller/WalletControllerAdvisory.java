package com.wallet.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.wallet.custom.exception.WalletCustomException;
@RestControllerAdvice
public class WalletControllerAdvisory {
	
	@ExceptionHandler({WalletCustomException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleWalletException(WalletCustomException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public Map<String,String> handleValidationRules(MethodArgumentNotValidException ex)
	{
		Map<String, String> errorsWallet = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errorsWallet.put(fieldName, errorMessage);
		});
		return errorsWallet; 
		
	}

}
