package com.asu.ss.secure_banking_system.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.asu.ss.secure_banking_system.model.SessionFactoryUtil;
import com.asu.ss.secure_banking_system.model.TAARequestEntity;
import com.asu.ss.secure_banking_system.model.TransactionEntity;
import com.sbs.model.user.User;

public class ProcessTAAService {

	public User getUserDetails(User user)
	{
		try
		{
			SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			user = (User)session.get(User.class, user.getUserID());
			session.getTransaction().commit();
			session.close();
			return user;
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return null;
	}		
	
	
	public List<TransactionEntity> getTransactionDetails(User user)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM TransactionEntity TE WHERE TE.userID = :userID";
		Query query = session.createQuery(hql);
		query.setParameter("userID", user.getUserID());
		List<TransactionEntity> requestList = query.list();
		session.getTransaction().commit();
		session.close();
		return requestList;
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return null;
	}		
}

