package com.asu.ss.secure_banking_system.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.asu.ss.secure_banking_system.model.SessionFactoryUtil;
import com.asu.ss.secure_banking_system.model.TAARequestEntity;
import com.sbs.model.user.User;

public class TAANotificationService {

	public List<TAARequestEntity> getAllTAARequestsForUser(User user)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM TAARequestEntity TAA WHERE TAA.assignedTo = :user";
		Query query = session.createQuery(hql);
		query.setParameter("user", user);
		List<TAARequestEntity> requestList = query.list();
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
