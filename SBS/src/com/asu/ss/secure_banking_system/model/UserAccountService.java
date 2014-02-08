package com.asu.ss.secure_banking_system.model;

import java.util.ArrayList;




import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserAccountService {
	private String userID;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
	public List<AccountEntity> getAccountsForUser() throws Exception
	{
		
		List<AccountEntity> accEntityList = new ArrayList<AccountEntity>();
		if(userID == null)
		{
			throw new Exception("Invalid user ID!!");
		}
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			Query query = session.createQuery("from AccountEntity where userID = :userID ");
			query.setParameter("userID", userID);
			
			accEntityList = query.list();
		
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			session.close();
		}
		return accEntityList;
	}
	
	
	

}
