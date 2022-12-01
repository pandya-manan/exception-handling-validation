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
	
	public Wallet addNewWallet(Wallet addWallet)
	{
		try
		{
			System.out.println("The wallet details to be added into the database is: "+addWallet.toString());
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
		catch(Exception e)
		{
			System.out.println("The exception occured while adding new record to database is: "+e.getMessage());
			Wallet negativeResponse=new Wallet();
			negativeResponse.setWalletAccountHolderName(null);
			negativeResponse.setWalletBalance(null);
			negativeResponse.setWalletId(null);
			return negativeResponse;
		}
		
	}
	
	public String addAccountBalanceByWalletId(Integer addBalanceAmount, Integer walletId)
	{
		try
		{
			Wallet wallet=daoLayer.findWalletByWalletId(walletId);
			Integer totalBalance=wallet.getWalletBalance()+addBalanceAmount;
			wallet.setWalletBalance(totalBalance);
			daoLayer.save(wallet);
			return "The wallet was updated with new balance: "+totalBalance+" for walletId: "+walletId;
		}
		catch(Exception e)
		{
			System.out.println("The addition of funds causes exception: "+e.getMessage());
			return "The deposit could not happen and error has occured!";
		}
		
	}
	
	public String withdrawFundsByWalletId(Integer withdrawalAmount, Integer walletId)
	{
		try
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
		catch(Exception e)
		{
			System.out.println("The exception occured while withdrawing funds to the account: "+e.getMessage());
			String negativeMessage="The withdrawal of funds for walletId: "+walletId+" did not occur";
			return negativeMessage;
		}
				
	}
	
	public String retrieveAccountBalanceByWalletId(Integer walletId)
	{
		try
		{
			Wallet wallet=daoLayer.findWalletByWalletId(walletId);
			return "The wallet Balance for walletId: "+walletId+" is: "+wallet.getWalletBalance();
		}
		catch(Exception e)
		{
			return "The exception occured to get the wallet balance based on walletId is: "+e.getMessage();
		}
		
	}
	
	public Wallet retrieveWalletDetailsByWalletId(Integer walletId)
	{
		try
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
		catch(Exception e)
		{
			System.out.println("The exception while getting details of wallet is: "+e.getMessage());
			Wallet negativeResponse=new Wallet();
			negativeResponse.setWalletAccountHolderName(null);
			negativeResponse.setWalletBalance(null);
			negativeResponse.setWalletId(null);
			return negativeResponse;
		}
		
	}
	
	public String deleteWalletByWalletId(Integer walletId)
	{
		try
		{
			daoLayer.deleteById(walletId);
			return ("Wallet was deleted from database successfully");
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while deletion is: "+e.getMessage());
			return ("Wallet could not be deleted based on walletId. Error occured!");
		}
	}
	
	public List<Wallet> transferFunds(Integer transferAmount, Integer fromWalletId, Integer toWalletId)
	{
		try
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
		catch(Exception e)
		{
			System.out.println("The fund transfer was not successful due to : "+e.getMessage());
			List<Wallet> wallets=new ArrayList<Wallet>();
			return wallets;
		}
	}
	

}
