package com.sbs.model.transaction;

import java.util.Date;

public class Transaction {

	private int txnId;
	private String txnType;
	private Date txnDate;
	private String fromAccountId;
	private String toAccountId;
	private String fromUserId;
	private String toUserId;
	private double amount;
	private double balance;
	private String details;
	private String status;
	/*private boolean authRequestedBool;
	private boolean authorizedBool;*/
	/*private String authRequested;
	private String authorized;*/

	public Transaction () {
		this.status = "none";
	}
	public Transaction(TransactionType txnType, String fromAccountId,
			String toAccountId, String fromUserId, String toUserId,
			Double amount, Double balance, String details) {
		this.txnType = txnType.getTxnTypeCode();
		this.txnDate = new Date();
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.amount = amount;
		//this.balance = getBalanceFromAcct();
		this.balance = balance;
		this.details = details;
		this.status = "none";
		//setAuthRequested(false);
		//setAuthorized(true);
/*		setAuthorizedBool(false);
		setAuthRequestedBool(true);
*/
	}
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public String getFromAccountId() {
		return fromAccountId;
	}
	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	public String getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*public boolean isAuthRequested() {
		return isAuthRequested;
	}
	public void setAuthRequested(boolean isAuthRequested) {
		this.isAuthRequested = isAuthRequested;
		if (isAuthRequested) {
			setAuthRequested("Y");
		}
	}
	public boolean isAuthorized() {
		return isAuthorized;
	}
	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
		if (isAuthorized) {
			setAuthorized("Y");
		}
	}*/
	/*public String getAuthRequested() {
		return authRequested;
	}
	public void setAuthRequested(String authRequested) {
		this.authRequested = authRequested;
		if (null != authRequested && authRequested.equalsIgnoreCase("Y")) {
			this.authRequestedBool = true;
		}
	}
	public String getAuthorized() {
		return authorized;
	}
	public void setAuthorized(String authorized) {
		this.authorized = authorized;
		if (null != authorized && authorized.equalsIgnoreCase("Y")) {
			this.authorizedBool = true;
		}
	}*/
	/*public boolean isAuthRequestedBool() {
		return authRequestedBool;
	}
	public void setAuthRequestedBool(boolean authRequestedBool) {
		this.authRequestedBool = authRequestedBool;
		if (authRequestedBool) {
			this.authRequested = "Y";
		}
	}
	public boolean isAuthorizedBool() {
		return authorizedBool;
	}
	public void setAuthorizedBool(boolean authorizedBool) {
		this.authorizedBool = authorizedBool;
		if (authorizedBool) {
			this.authorized = "Y";
		}
	}*/	
}
