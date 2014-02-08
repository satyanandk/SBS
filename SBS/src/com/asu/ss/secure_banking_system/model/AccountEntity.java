package com.asu.ss.secure_banking_system.model;
//just for test
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCT_DTLS")
public class AccountEntity {

	@Id 
	@Column(name = "account_id",length = 10)
	private String accountID;
	
	@Column(name = "acct_name", length = 20)
	private String acctName;
	
	@Column(name = "acct_balance")
	private double acctBalance;
	
	@Column(name="user_id", length = 15)
	private String userID;

	
	

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	/**
	 * @return the acctName
	 */
	public String getAcctName() {
		return acctName;
	}

	/**
	 * @param acctName the acctName to set
	 */
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	/**
	 * @return the acctBalance
	 */
	public double getAcctBalance() {
		return acctBalance;
	}

	/**
	 * @param acctBalance the acctBalance to set
	 */
	public void setAcctBalance(double acctBalance) {
		this.acctBalance = acctBalance;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
