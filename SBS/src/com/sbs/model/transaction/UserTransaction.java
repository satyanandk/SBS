package com.sbs.model.transaction;

import java.util.Date;

public class UserTransaction {
	private long txnId;
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
}
