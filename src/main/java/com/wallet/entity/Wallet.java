package com.wallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name="WALLET")
public class Wallet {
	
	@Id
	@Column(name="WALLET_ID")
	@Min(value=1,message="Please give the positive value for walletId")
	private Integer walletId;
	
	@Column(name="WALLET_ACCOUNT_HOLDER_NAME",length=355)
//	@Pattern(regexp="[A-Za-z]+[\\s]+[A-Za-z]{3,355}", message= "Name must be alphabets having min 3 to max 355 chars.")
	@NotBlank(message="Wallet Name should not be empty")
	@NotNull(message="Wallet Name should not be null")
	private String walletAccountHolderName;
	
	@Column(name="WALLET_BALANCE")
	@Min(value=0)
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
