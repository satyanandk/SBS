package com.asu.ss.secure_banking_system.model;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.catalina.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class AccountService {

	private String accountID;
	private AccountEntity accEntity;
	private String userID;

	/**
	 * @return the accountID
	 */
	
	
	public String getAccountID() {
		return accountID;
	}

	/**
	 * @param accountID the accountID to set
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
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

	public AccountEntity getAccountSummary() throws Exception
	{
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			System.out.println("account = "+accountID);
			this.accEntity = (AccountEntity)session.get(AccountEntity.class, accountID);
			if(accEntity == null)
				throw new Exception("Invalid Account passed ["+accountID+"]");
			
			if(!userID.equals(accEntity.getUserID()))
			{
				throw new Exception("Account does not belong to you!");
			}
		}
		catch(Exception e)
		{
			System.out.println("error satya in Account Service : "+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally {
			session.close();
		}
		
		return this.accEntity;
	}

	public AccountEntity getAccountDetails(String accountID) throws Exception
	{
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			System.out.println("account = "+accountID);
			this.accEntity = (AccountEntity)session.get(AccountEntity.class, accountID);
			if(accEntity == null)
				throw new Exception("Invalid Account passed ["+accountID+"]");
		}
		catch(Exception e)
		{
			System.out.println("error satya in Account Service : "+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally {
			session.close();
		}
		
		return this.accEntity;
	}
	/* updates the balance  */
	public AccountEntity updateBalance(String accountID,double tranAmount,char typeOfUpdate) throws Exception
	{
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			this.accEntity = (AccountEntity)session.get(AccountEntity.class, accountID);
			if(accEntity == null)
				throw new Exception("Invalid Account passed ["+accountID+"]");

		}
		catch(Exception  e)
		{
			System.out.println("error satya in Account Service1 : "+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally {
			session.close();
		}
		double availableBalance = 0.0;

		if(typeOfUpdate == 'C')
		{
			availableBalance = accEntity.getAcctBalance() + tranAmount;
		}
		else if(typeOfUpdate == 'D')
		{
			availableBalance = accEntity.getAcctBalance() - tranAmount;
			if(availableBalance < 0.00)
			{
				throw new Exception("Balance is Insufficient");
			}

		}
		accEntity.setAcctBalance(availableBalance);

		return accEntity;

	}
	
	public String createAccountForUser() throws Exception
	{
		String accountID = "";
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

		final int PW_LENGTH = 10;
		Random rnd = new SecureRandom();
		StringBuilder accID = new StringBuilder();
		for (int i = 0; i < PW_LENGTH; i++)
			accID.append(chars.charAt(rnd.nextInt(chars.length())));
		
		
		Transaction tx = null;
		accEntity = new AccountEntity();
		try
		{
			tx = session.beginTransaction();
			accEntity.setAccountID(accID.toString());
			accEntity.setAcctBalance(0);
			accEntity.setUserID(userID);
			session.save(accEntity);
			
			tx.commit();
			System.out.println("user ID = "+accEntity.getUserID() +"\naccount ID = "+accEntity.getAccountID()+"\n balance = "+accEntity.getAcctBalance());
			
		}
		catch(Exception e)
		{
			tx.rollback();
			throw e;
		}
		finally
		{
			session.close();
		}
		return accEntity.getAccountID().toString();
	}
	
	public static boolean isValidAccount(String accountID, String resultMessage)
	{
		resultMessage="";
		if(accountID.isEmpty())
		{
			resultMessage = "Account ID must be entered!";
			return false;
		}
		if(accountID.length() > 10)
		{
			resultMessage = "Account ID ["+accountID+"] is not valid";
			return false;
		}
			
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		try
		{
			AccountEntity accEntity = new AccountEntity();
			session.beginTransaction();
			System.out.println("account = "+accountID);
			accEntity = (AccountEntity)session.get(AccountEntity.class, accountID);
			if(accEntity == null)
			{
				resultMessage = "Account ID ["+accountID+"] is not valid";
				return false;
			}
		}
		catch(Exception e)
		{
			resultMessage = "Account ID ["+accountID+"] is not valid";
			System.out.println("error satya in Account Service : "+e.getMessage());
			e.printStackTrace();
			return false;
			
		}
		finally {
			session.close();
		}
		return true;
	}
}
