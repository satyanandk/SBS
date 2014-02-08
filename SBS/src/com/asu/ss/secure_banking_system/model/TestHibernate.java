package com.asu.ss.secure_banking_system.model;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.asu.ss.secure_banking_system.service.AdminNotificationService;


public class TestHibernate {

	public static void main(String[] args) {
	
		AdminNotificationService svc = new AdminNotificationService();
	
	/*	SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		/*
		UserEntity user = new UserEntity();
		user.setUserID("defg");
		RoleEntity userRole = new RoleEntity();
		userRole.setRoleID(4);
		userRole.setRoleName("new role");
		user.setRole(userRole);
		session.save(userRole);
		session.save(user);
		*/
		/*
		RoleRequestEntity re = new RoleRequestEntity();
		UserEntity ue = new UserEntity();
		RoleEntity roe = new RoleEntity();
		roe.setRoleID(463);
		roe.setRoleName("pqrswet");
		ue.setUserID("abcdeew");
		ue.setRole(roe);
		session.save(roe);
		session.save(ue);
		
		re.setUser(ue);
		re.setRequestID(2263);
		re.setRequestTime(new Date());
		re.setValidated(false);
		re.setAssigned(false);
		re.setRole(roe);
		re.setRequestedBy(ue);
		
		RequestEntity rwe = re;
		session.save(rwe);
		session.getTransaction().commit();
		session.close();
		*/
	}

}
