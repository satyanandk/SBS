package com.asu.ss.secure_banking_system.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sbs.model.transaction.TransactionManager;
import com.sbs.model.transaction.TransactionType;

public class MerchantPayConfService {

	private String merchantUserID;
	private String cusUserID;
	private double dAmount;
	private String remarks;
	private String cusAccount;
	private String payID;
	private TranConfResult tranConfResult;
	
	
	public TranConfResult getTranConfResult() {
		return tranConfResult;
	}

	public void setTranConfResult(TranConfResult tranConfResult) {
		this.tranConfResult = tranConfResult;
	}

	public MerchantPayConfService(String merchantUserID, String cusUserID, double dAmount,String cusAccount,String payID)
	{
		System.out.println("1st point of entry merchantUserID = "+merchantUserID);
		this.merchantUserID = merchantUserID;
		this.cusUserID = cusUserID;
		this.dAmount = dAmount;
		this.cusAccount = cusAccount;
		this.payID = payID;
	}
	public MerchantPayConfService(String payID)
	{
		this.payID = payID;
		
	}
	
	public int isPaymentDoneAlready()
	{
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		MerchantRequestEntity mr = new MerchantRequestEntity();
		try
		{
			session.beginTransaction();
			mr = (MerchantRequestEntity)session.get(MerchantRequestEntity.class, Long.valueOf(payID));
			if(mr == null)
				return 1;
			else if(mr.isPaidFlag())
				return 2;
			else
				return 0;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			session.close();
		}
		
		return 0;
	}
	public String getMerchantAccountID() throws Exception
	{
		String merAccountID;
		
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		try
		{
			Transaction tx = null;
			tx = session.beginTransaction();
			
			String hql = "from AccountEntity where user_id =:userID";
			Query qry = session.createQuery(hql);
			
				qry.setParameter("userID", merchantUserID);
				
				List<AccountEntity> accEntityresults = qry.list();
				
				System.out.println("no. pf rows returned are :"+accEntityresults.size());

				if(accEntityresults.isEmpty())
					throw new Exception("Merchant does not have a account with bank!");
				
				merAccountID = accEntityresults.get(0).getAccountID().toString();
			
		}
		finally
		{
			session.close();
		}
		return merAccountID;
	}
	public void confirmPayment() throws Exception
	{
		AccountService accService = new AccountService();
		AccountEntity cusAccEntity = new AccountEntity();
		String merchantAccountID;
		MerchantRequestEntity mr = new MerchantRequestEntity();
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			mr = (MerchantRequestEntity)session.get(MerchantRequestEntity.class, Long.valueOf(payID));
			
			if(mr.isPaidFlag())
				throw new Exception("Payment is already made for this!!");
			mr.setPaidFlag(true);
			session.flush();
			
			System.out.println("in Merchant Payment Conf Service cusAccount = "+cusAccount);
			cusAccEntity = accService.getAccountDetails(cusAccount);
			if(cusAccEntity == null)
				throw new Exception("Account is invalid!");
			
			System.out.println("User ID of the Account :"+cusAccEntity.getUserID());
			if(!cusAccEntity.getUserID().equals(cusUserID))
				throw new Exception("Account does not belong to you!");
			
			
			merchantAccountID = getMerchantAccountID();
			
			System.out.println("in Merchant Payment Conf Service CusAccount = "+cusAccount);
			System.out.println("in Merchant Payment Conf Service merchantAccount = "+merchantAccountID);
			TransferService transferService = new TransferService(cusAccount,merchantAccountID,TypeOfTransfer.TRANSFER,dAmount,cusUserID);
			transferService.fundTransferBetweenAccounts();
			tranConfResult = transferService.getTranConfResult();
			tranConfResult.setToAccountId("Not Applicable");
			tx.commit();
			
			try
			{
				com.sbs.model.transaction.Transaction t = new com.sbs.model.transaction.Transaction(TransactionType.PAYMENT_GIVEN,
						cusAccount,merchantAccountID,cusUserID,merchantUserID,dAmount,tranConfResult.getBalance(),"Pmnt done");
				
					TransactionManager.createTransaction(t);
			}
			catch(Exception e)
			{
				
			}
		
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
	}
}
