package com.wallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="WALLET")
public class Wallet {
	
	@Id
	@Column(name="WALLET_ID")
	private Integer walletId;
	
	@Column(name="WALLET_ACCOUNT_HOLDER_NAME",length=355)
	private String walletAccountHolderName;
	
	@Column(name="WALLET_BALANCE")
	private Integer walletBalance;

	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public String getWalletAccountHolderName() {
		return walletAccountHolderName;
	}

	public void setWalletAccountHolderName(String walletAccountHolderName) {
		this.walletAccountHolderName = walletAccountHolderName;
	}

	public Integer getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(Integer walletBalance) {
		this.walletBalance = walletBalance;
	}

	@Override
	public String toString() {
		return "Wallet [walletId=" + walletId + ", walletAccountHolderName=" + walletAccountHolderName
				+ ", walletBalance=" + walletBalance + "]";
	}
	
	
	

}
