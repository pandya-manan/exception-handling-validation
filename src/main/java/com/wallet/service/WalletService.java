package com.wallet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.custom.exception.WalletCustomException;
import com.wallet.dao.WalletDaoLayer;
import com.wallet.entity.Wallet;

@Service
public class WalletService {
	
	@Autowired(required=true)
	public WalletDaoLayer daoLayer;
	
	public Wallet addNewWallet(Wallet addWallet) throws WalletCustomException
	{
		
		Wallet toSave=addWallet;
		daoLayer.save(toSave);
		Wallet verificationWallet=daoLayer.findWalletByWalletId(addWallet.getWalletId());
		if(verificationWallet==null)
		{
			throw new WalletCustomException("The new wallet details "+addWallet+" could not be added to DB.");
			
		}
		else
		{
			System.out.println("The wallet "+addWallet+" was added to database successfully");
			return verificationWallet;
		}
	}
	
	public String addAccountBalanceByWalletId(Integer addBalanceAmount, Integer walletId) throws WalletCustomException
	{
		
		if(addBalanceAmount==0)
		{
			throw new WalletCustomException("The add balance amount "+addBalanceAmount+" is zero, cannot do this action ");
		}
		else
		{
			Wallet wallet=daoLayer.findWalletByWalletId(walletId);
			System.out.println(wallet.toString());
			Integer availableBalance=wallet.getWalletBalance();
			Integer totalBalance=availableBalance+addBalanceAmount;
			wallet.setWalletBalance(totalBalance);
			System.out.println("To save: "+wallet.toString());
			daoLayer.save(wallet);
			return "The wallet was updated with new balance: "+totalBalance+" for walletId: "+walletId;
		}
			
	}
	
	public String withdrawFundsByWalletId(Integer withdrawalAmount, Integer walletId) throws WalletCustomException
	{
		
		
			Wallet wallet=daoLayer.findWalletByWalletId(walletId);
			Integer availableBalance=wallet.getWalletBalance();
			
			if(availableBalance<withdrawalAmount)
			{
				throw new WalletCustomException("The available balance "+availableBalance+" is lesser than the withdrawal amount "+withdrawalAmount);
			}
			else
			{
				Integer totalBalance=availableBalance-withdrawalAmount;
				wallet.setWalletBalance(totalBalance);
				daoLayer.save(wallet);
				return "The wallet was updated with new balance: "+totalBalance+" for walletId: "+walletId;
			}
				
				
	}
	
	public String retrieveAccountBalanceByWalletId(Integer walletId) throws WalletCustomException
	{
		
		
			Wallet wallet=daoLayer.findWalletByWalletId(walletId);
			if(wallet==null)
			{
				throw new WalletCustomException("The wallet was not found for walletId: "+walletId);
			}
			return "The wallet Balance for walletId: "+walletId+" is: "+wallet.getWalletBalance();
		
		
		
	}
	
	public Wallet retrieveWalletDetailsByWalletId(Integer walletId) throws WalletCustomException
	{
		
		
			Wallet obtainedWallet=daoLayer.findWalletByWalletId(walletId);
			if(obtainedWallet!=null)
			{
				return obtainedWallet;
			}
			else
			{
				throw new WalletCustomException("The wallet details could not be retrieved using the walletId :"+walletId);
			}
		
		
		
	}
	
	public String deleteWalletByWalletId(Integer walletId) throws WalletCustomException
	{
			Optional<Wallet> department=daoLayer.findById(walletId);		
			if(department.isEmpty())
			{
				throw new WalletCustomException("The wallet was not present with walletId "+walletId+" hence deletion is not possible");
			}
			daoLayer.deleteById(walletId);
			return ("Wallet was deleted from database successfully for walletId: "+walletId);
		
	}
	
	public List<Wallet> transferFunds(Integer transferAmount, Integer fromWalletId, Integer toWalletId) throws WalletCustomException
	{
		
		
			Wallet fromWallet=daoLayer.findWalletByWalletId(fromWalletId);
			Integer availableBalance=fromWallet.getWalletBalance();
			if(availableBalance<transferAmount)
			{
				throw new WalletCustomException("Insufficient funds ,cannot transfer funds from account with walletId: "+fromWalletId);
			}
			Wallet toWallet=daoLayer.findWalletByWalletId(toWalletId);
			Integer availableBalanceTo=toWallet.getWalletBalance();
			
			System.out.println("The wallet balance in from wallet Id "+fromWalletId+" before transfer: "+fromWallet.getWalletId());
			System.out.println("The wallet balance in to wallet Id "+toWalletId+" before transfer: "+toWallet.getWalletId());
			Integer setBalanceFrom=availableBalance-transferAmount;
			fromWallet.setWalletBalance(setBalanceFrom);
			daoLayer.save(fromWallet);
			
			Integer setBalanceTo=availableBalanceTo+transferAmount;
			toWallet.setWalletBalance(setBalanceTo);
			daoLayer.save(toWallet);
			
			List<Wallet> wallets=new ArrayList<Wallet>();
			wallets.add(fromWallet);
			wallets.add(toWallet);
			
			
			
			System.out.println("The fund transfer was successful!");
			
			return wallets;
		
		
	}
	

}
