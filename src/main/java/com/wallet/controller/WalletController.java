package com.wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.entity.Wallet;
import com.wallet.service.WalletService;

@RestController
@RequestMapping("wallet")
public class WalletController {
	
	@Autowired(required=true)
	public WalletService service;
		
	@PostMapping("/registerWallet")
	public Wallet registerWallet(@RequestBody Wallet addWallet)
	{
		Wallet responseFromService=service.addNewWallet(addWallet);
		return responseFromService;
	}
	
	@GetMapping("walletdetails/{walletId}")
	public Wallet walletDetails(@PathVariable("walletId") Integer walletId)
	{
		Wallet responseFromService=service.retrieveWalletDetailsByWalletId(walletId);
		return responseFromService;
	}
	
	@GetMapping("depositFunds")
	public String depositFunds(@RequestParam("addFund")Integer additionalFund, @RequestParam("walletId")Integer walletId)
	{
		System.out.println("The fund amount from user is: "+additionalFund);
		System.out.println("The walletId from user is: "+walletId);
		String responseFromService=service.addAccountBalanceByWalletId(additionalFund, walletId);
		return responseFromService;
	}
	
	@GetMapping("withdrawFunds")
	public String withdrawFunds(@RequestParam("withdrawFund")Integer withdrawalFund, @RequestParam("walletId")Integer walletId)
	{
		System.out.println("The fund amount from user is: "+withdrawalFund);
		System.out.println("The walletId from user is: "+walletId);
		String responseFromService=service.withdrawFundsByWalletId(withdrawalFund, walletId);
		return responseFromService;
	}
	
	
	@GetMapping("walletBalance/{walletId}")
	public String walletBalanceDetails(@PathVariable Integer walletId)
	{
		String responseFromService=service.retrieveAccountBalanceByWalletId(walletId);
		return responseFromService;
	}
	
	@DeleteMapping("deleteWallet/{walletId}")
	public String deleteWallet(@PathVariable Integer walletId)
	{
		String responseFromService=service.deleteWalletByWalletId(walletId);
		return responseFromService;
	}
	
	
	@GetMapping("transferFunds")
	public List<Wallet> transferFunds(@RequestParam("fromWalletId") Integer fromWalletId, @RequestParam("toWalletId") Integer toWalletId, @RequestParam("transferAmount") Integer transferAmount)
	{
		List<Wallet> wallets=service.transferFunds(transferAmount, fromWalletId, toWalletId);
		return wallets;
	}

}
