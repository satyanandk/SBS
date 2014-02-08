package com.asu.ss.secure_banking_system.service;

import java.util.ArrayList;




import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.asu.ss.secure_banking_system.model.RequestEntity;
import com.asu.ss.secure_banking_system.model.SessionFactoryUtil;

public class AdminNotificationService {

	public List<RequestEntity> getRoleRequests(String userID)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String hql = "FROM RoleRequestEntity RE WHERE RE.isValidated = 'false' AND RE.requestedBy != '"+userID+"'"
				+ " AND RE.requestForUser != '"+userID+"'";
		Query query = session.createQuery(hql);
		List<RequestEntity> requestList = query.list();
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
	
	public List<RequestEntity> getTAARequests(String userID)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String hql = "FROM TAARequestEntity RE WHERE RE.isValidated = 'false' AND RE.requestedBy != '"+userID+"'";
		Query query = session.createQuery(hql);
		List<RequestEntity> requestList = query.list();
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
	
	/**
	 * @return
	 */
	public ArrayList<RequestEntity> getAllRequestes(String userID)
	{
		ArrayList<RequestEntity> allRequests = new ArrayList<RequestEntity>();
		List<RequestEntity> requestList = getRoleRequests(userID);
		if(requestList.size()>0)
		for(int i=0; i<requestList.size();i++)
		{
			allRequests.add((RequestEntity)requestList.get(i));
		}
		requestList = getTAARequests(userID);
		if(requestList.size()>0)
		for(int i=0; i<requestList.size();i++)
		{
			allRequests.add((RequestEntity)requestList.get(i));
		}
		return allRequests;
	}
	
}
