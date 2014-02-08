package com.asu.ss.secure_banking_system.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.asu.ss.secure_banking_system.model.SessionFactoryUtil;
import com.asu.ss.secure_banking_system.model.TAARequestEntity;
import com.asu.ss.secure_banking_system.model.UserEntity;
import com.sbs.model.user.User;

public class TAARequestService {

	public User getUserVerfied(String username, String dob, String emailID)
	{
		//yet to be implemented
		User ue=null;
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM User WHERE userID= :userID AND EMAILID= :email AND DOB= :dob";
		Query query = session.createQuery(hql);
		query.setParameter("userID", username);
		query.setParameter("email", emailID);
		query.setParameter("dob", dob);		
		List<User> requestList = query.list();
		ue = requestList.size()>0?(User)requestList.get(0):null;
		session.getTransaction().commit();
		session.close();
		return ue;
	}
	
	public void createTAARequest(User user, String description)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		//
		TAARequestEntity taa = new TAARequestEntity();
		taa.setAssigned(false);
		taa.setDescription(description);
		taa.setRequestedBy(user);
		taa.setValidated(false);
		taa.setRequestTime(new Date());
		session.save(taa);
		session.getTransaction().commit();
		session.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}

	}
	
	public boolean checkIfValidTAARequest(TAARequestEntity taa)
	{
		return true;
	}
	
}
