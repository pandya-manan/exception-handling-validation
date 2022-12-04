package com.wallet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wallet.entity.Wallet;

@Repository
public interface WalletDaoLayer extends JpaRepository<Wallet,Integer> {
	
	Integer findWalletBalanceByWalletId(Integer walletId);
	
	@Query("SELECT a.walletBalance from Wallet a WHERE a.walletId= :walletId")
	public Integer getWalletBalance(Integer walletId);
		
	Wallet findWalletByWalletId(Integer walletId);
	
	

}
