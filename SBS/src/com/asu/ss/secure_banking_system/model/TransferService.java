package com.asu.ss.secure_banking_system.model;



import java.util.Date;







import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jgroups.demos.TotalOrder;

import com.sbs.model.transaction.TransactionManager;
import com.sbs.model.transaction.TransactionType;




public class TransferService {

	private String fromAccount;
	private String toAccount;
	private String account;
	private TypeOfTransfer typeOfTrans;
	private double tranAmt;
	private String userID;
	private TranConfResult tranConfResult;
	
	public TranConfResult getTranConfResult() {
		return tranConfResult;
	}
	public void setTranConfResult(TranConfResult tranConfResult) {
		this.tranConfResult = tranConfResult;
	}
	/**
	 * @return the fromAccount
	 */
	public String getFromAccount() {
		return fromAccount;
	}
	/**
	 * @param fromAccount the fromAccount to set
	 */
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	/**
	 * @return the toAccount
	 */
	public String getToAccount() {
		return toAccount;
	}
	/**
	 * @param toAccount the toAccount to set
	 */
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the typeOfTrans
	 */
	public TypeOfTransfer getTypeOfTrans() {
		return typeOfTrans;
	}
	/**
	 * @param typeOfTrans the typeOfTrans to set
	 */
	public void setTypeOfTrans(TypeOfTransfer typeOfTrans) {
		this.typeOfTrans = typeOfTrans;
	}
	/**
	 * @return the tranAmt
	 */
	public double getTranAmt() {
		return tranAmt;
	}
	/**
	 * @param tranAmt the tranAmt to set
	 */
	public void setTranAmt(double tranAmt) {
		this.tranAmt = tranAmt;
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
	
	/* This constructor to be used when transfer between two accounts is involved*/
	public TransferService(String fromAccount, String toAccount,
			TypeOfTransfer typeOfTrans, double tranAmt, String userID) {
		
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.typeOfTrans = typeOfTrans;
		this.tranAmt = tranAmt;
		this.userID = userID;
	}
	/*This Constructor to be used only when a Debit/Credit to be made */
	public TransferService(String fromOrToAccount, TypeOfTransfer typeOfTrans, double tranAmt, String userID)
	{
		this.account = fromOrToAccount;
		this.typeOfTrans = typeOfTrans;
		this.tranAmt = tranAmt;
		this.userID = userID;
	}
	public void DebitOrCreditAccount() throws Exception{
		AccountService accountService = new AccountService();
		
		AccountEntity accEntity;
		//TransactionKey transactionKey = new TransactionKey();
		TransactionEntity transaction = new TransactionEntity();
		TranConfResult tranConfResult = new TranConfResult();
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		char typeOfTransfer;
		if(typeOfTrans == TypeOfTransfer.CREDIT)
			typeOfTransfer = 'C';
		else
			typeOfTransfer = 'D';
		
		try {
		
			tx = session.beginTransaction();
			//transactionKey.setTransactionID(2);
			
			transaction.setTranType(typeOfTransfer);
		
			transaction.setAccountId(account);
			transaction.setTranAmount(tranAmt);
			transaction.setTranDate(new Date());
			//transaction.setTransactionKey(transactionKey);
			transaction.setUserID(accountService.getAccountDetails(account).getUserID());
			transaction.setTranCreatedByUser(userID);
			accEntity = accountService.updateBalance(account,tranAmt, typeOfTransfer);
			
			if(!accEntity.getUserID().equals(userID))
				throw new Exception("The From account is invalid or does not belong to you");
			
			transaction.setBalance(accEntity.getAcctBalance());
			
			session.save(transaction);
			
			
			
			session.update(accEntity);
			//tranConfResult.setTransactionId(1);
			tranConfResult.setBalance(accEntity.getAcctBalance());
			tranConfResult.setAmount(tranAmt);
			if(typeOfTransfer == 'C') {
				tranConfResult.setFromAccountId("Not Applicable");
				tranConfResult.setToAccountId(account);
			}
			if(typeOfTransfer == 'D') {
				tranConfResult.setFromAccountId(account);
				tranConfResult.setToAccountId("Not Applicable");
			}
		
			this.setTranConfResult(tranConfResult);
			
			tx.commit();
			
			try
			{
				if(typeOfTransfer == 'C')
				{
					com.sbs.model.transaction.Transaction t = new com.sbs.model.transaction.Transaction(TransactionType.CREDIT," ",account,
							"",userID,tranAmt,accEntity.getAcctBalance(),"Credit Transaction");
					
					TransactionManager.createTransaction(t);
				}
				else if(typeOfTransfer == 'D')
				{
					com.sbs.model.transaction.Transaction t = new com.sbs.model.transaction.Transaction(TransactionType.DEBIT,account," ",
							userID," ",tranAmt,accEntity.getAcctBalance(),"Debit Transaction");
					
					TransactionManager.createTransaction(t);
				}
				
			}
			catch(Exception e)
			{
				
			}
		}
		catch(Exception e)
		{
			if(tx!=null)	tx.rollback();
			throw e;
		}
		finally {
			session.close();
		}
		
		
	}
	public void fundTransferBetweenAccounts() throws Exception{
		
		AccountService fromAccSer = new AccountService();
		AccountService toAccSer = new AccountService();
		AccountEntity frmAccEnt;
		AccountEntity toAccEnt;
		TransactionEntity frmTransaction = new TransactionEntity();
		//TransactionKey frmTransactionKey = new TransactionKey();
		
		TransactionEntity toTransaction = new TransactionEntity();
		//TransactionKey toTransactionKey = new TransactionKey();
		TranConfResult tranConfResult = new TranConfResult();
		Transaction tx = null;
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		
		try {
		
			tx = session.beginTransaction();
		
			//frmTransactionKey.setTransactionID(2);
			frmTransaction.setTranType('D');
			frmTransaction.setAccountId(fromAccount);
			frmTransaction.setTranAmount(tranAmt);
			frmTransaction.setTranDate(new Date());
			//frmTransaction.setTransactionKey(frmTransactionKey);
			frmTransaction.setUserID(fromAccSer.getAccountDetails(fromAccount).getUserID());
			frmTransaction.setTranCreatedByUser(userID);
			frmAccEnt = fromAccSer.updateBalance(fromAccount,tranAmt, 'D');
			
			if(!frmAccEnt.getUserID().equals(userID))
				throw new Exception("The From account is invalid or does not belong to you");
			
			frmTransaction.setBalance(frmAccEnt.getAcctBalance());
			
			//toTransactionKey.setTransactionID(1);
			toTransaction.setTranType('C');
			toTransaction.setAccountId(toAccount);
			toTransaction.setTranAmount(tranAmt);
			toTransaction.setTranDate(new Date());
			//toTransaction.setTransactionKey(toTransactionKey);
			toTransaction.setUserID(toAccSer.getAccountDetails(toAccount).getUserID());
			toTransaction.setTranCreatedByUser(userID);
			toAccEnt = toAccSer.updateBalance(toAccount,tranAmt, 'C');
			toTransaction.setBalance(toAccEnt.getAcctBalance());
			
			
			String frmUserID = frmTransaction.getUserID();
			String toUserID = toTransaction.getUserID();
			
			System.out.println("From Account = "+frmTransaction.getAccountId());
			System.out.println("To Account = "+toTransaction.getAccountId());
			System.out.println("Ffrom User Id = "+frmTransaction.getUserID());
			System.out.println("TO user ID = "+toTransaction.getUserID());
			long x1 = ((Long)session.save(frmTransaction)).longValue();
			session.flush();
			System.out.println("Satya check 1 "+x1);
			
			//long x = frmTransaction.getTransactionKey().getTransactionID();
			//System.out.println("x = "+x);
			session.flush();
			//toTransactionKey.setTransactionID(x+1);
			session.save(toTransaction);
			
			session.flush();
			session.update(frmAccEnt);
			session.flush();
			session.update(toAccEnt);
		
			tranConfResult.setFromAccountId(fromAccount);
			tranConfResult.setToAccountId(toAccount);
			tranConfResult.setAmount(tranAmt);
			tranConfResult.setBalance(frmAccEnt.getAcctBalance());
			
			double dBal = frmAccEnt.getAcctBalance();
			this.setTranConfResult(tranConfResult);
			tx.commit();
			try
			{
			com.sbs.model.transaction.Transaction t = new com.sbs.model.transaction.Transaction(TransactionType.TRANSFER,fromAccount,toAccount,
					frmUserID,toUserID,tranAmt,dBal,"Transfer Transaction");
			
			TransactionManager.createTransaction(t);
			}
			catch(Exception e) {
				
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				if(tx!=null)	tx.rollback();
				throw e;
			}
			finally {
				session.close();
			}
		}
	
}
