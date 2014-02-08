package com.asu.ss.secure_banking_system.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.asu.ss.secure_banking_system.model.SessionFactoryUtil;
import com.asu.ss.secure_banking_system.model.TAARequestEntity;
import com.asu.ss.secure_banking_system.util.AuthorizationUtil;
import com.sbs.model.transaction.Transaction;
import com.sbs.model.transaction.TransactionManager;
import com.sbs.model.transaction.TransactionType;
import com.sbs.model.user.User;

public class TAAAssignmentService {

	public List<User> getAllInternalUsers(String userID)
	{
		RequestRoleService svc = new RequestRoleService();
		return svc.getAllInternalUsers(userID);
	}
	
	public void assignTAAtoUser(TAARequestEntity taa, User user)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		taa.setAssigned(true);
		taa.setAssignedTo(user);
		taa.setValidated(true);
		//taa transaction
		
		Transaction t = new Transaction(TransactionType.USR_ROLE_CHANGED,
				null, null, taa.getRequestedBy().getUserID(), null, 0.0, 0.0,
				"Technical account acces requested " + taa.getDescription());
		TransactionManager.createTransaction(t);

		session.update(taa);
		session.getTransaction().commit();
		session.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}	
}