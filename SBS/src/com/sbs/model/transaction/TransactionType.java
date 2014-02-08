package com.sbs.model.transaction;

public enum TransactionType {
	ADD_USER_ACCT("ADD_USR"),	//Tom
	MOD_USER_ACCT("MOD_USR"),	//Tom
	DEL_USER_ACCT("DEL_USR"),	//Tom
	TFR_USER_ACCT("TFR_USR"),	//Tom
	VIEW_USER_ACCT("VIEW_USR"),	//Tom
	AUTH_REQUESTED("AUTH_REQUESTED"),	//Sandip, Kushal
	AUTH_GIVEN("AUTH_GIVEN"),			//Sandip, Kushal
	VIEW_INT_TXN("VIEW_INT_TXN"),	// Kushal
	VIEW_EXT_TXN("VIEW_EXT_TXN"),	// Kushal
	VIEW_SYS_LOG("VIEW_SYS_LOG"),	// Kushal
	PAYMENT_REQUESTED("PAYMENT_REQUESTED"),	// Satya
	PAYMENT_GIVEN("PAYMENT_GIVEN"),	// Satya
	PAYMENT_SUBMITTED("PAYMENT_SUBMITTED"),	// Satya
	ADD_EXT_TXN("ADD_EXT_TXN"),	
	MOD_EXT_TXN("MOD_EXT_TXN"),	 
	DEL_EXT_TXN("DEL_EXT_TXN"), 
	USR_ROLE_CHANGED("USR_ROLE_CHANGED"), // Sandip
	CREDIT("CREDIT"),	// Satya
	DEBIT("DEBIT"),	// Satya
	TRANSFER("TRANSFER"), // Satya
	UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS");
	 
	private String txnTypeCode;
 
	private TransactionType(String s) {
		txnTypeCode = s;
	}
 
	public String getTxnTypeCode() {
		return txnTypeCode;
	}
}
