package com.asu.ss.secure_banking_system.model;

import org.hibernate.Session;

import com.sbs.model.crypto.PkiUtils;
import com.sbs.model.transaction.TransactionManager;
import com.sbs.model.transaction.TransactionType;
import com.sbs.model.user.UserManager;

public class MerchantService {

	private String merchantUserID;
	private String userID;
	private double dAmount;
	private String remarks;
	
	
	public String getMerchantUserID() {
		return merchantUserID;
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public void setMerchantUserID(String merchantUserID) {
		this.merchantUserID = merchantUserID;
	}


	public double getdAmount() {
		return dAmount;
	}


	public void setdAmount(double dAmount) {
		this.dAmount = dAmount;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public MerchantService(String merchantUserID, String userID, double dAmount, String remarks)
	{
		this.merchantUserID = merchantUserID;
		this.userID = userID;
		this.dAmount = dAmount;
		this.remarks = remarks;
	}
	
	public MerchantService()
	{
		super();
	}
	
	public MerchantRequestEntity getPaymentDetails(long payID) throws Exception
	{
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		MerchantRequestEntity mer;
		try
		{
			
			session.beginTransaction();
			mer = (MerchantRequestEntity)session.get(MerchantRequestEntity.class, payID);
			if(mer == null)
				throw new Exception("URL Tampered!!!");
		}
		catch(Exception e)
		{
			
			throw e;
		}
		finally
		{
			session.close();
		}
		
		return mer;
	}
	public void requestPayment() throws Exception
	{
		
		MerchantRequestEntity mr = new MerchantRequestEntity();
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		long paymentID;
		try
		{
			session.beginTransaction();
			mr.setCustomerUserID(userID);
			mr.setdAmount(dAmount);
			mr.setMerchantUserID(merchantUserID);
			mr.setRemarks(remarks);
			mr.setPaidFlag(false);
			
			session.save(mr);
			session.flush();
			session.getTransaction().commit();
			paymentID = mr.getMerchantRequestID();
	
			try
			{
				com.sbs.model.transaction.Transaction t = new com.sbs.model.transaction.Transaction(TransactionType.PAYMENT_REQUESTED,
					" ", " ",merchantUserID,userID,dAmount,0.00,"Pmnt Requested");
			
				TransactionManager.createTransaction(t);
			}
			catch(Exception e)
			{
				System.out.println("Exception for Kushal Table = "+e.getMessage());
			}
			
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw e;
		}
		finally
		{
			session.clear();
		}
		String url = "http://localhost:8080/SBS/CustomerPaymentServlet1?payID="+paymentID+"";

		String paymentSubject = "Payment Request for "+dAmount+" towards "+remarks+" ";
		StringBuilder message = new StringBuilder();
		message.append("Dear Customer, \n\n");
		message.append("You are requested to make a payment of ");
		message.append(dAmount);
		message.append(" towards ");
		message.append(remarks);
		message.append(" Please click the link below to process the payment\n\n");
		message.append(url);
		
		/*Here fetch the user's email from the profile with userID)*/
		OTPMailAPI.sendMail(UserManager.emailidreturn(userID), paymentSubject , message.toString());
		
		
	}
	
}
