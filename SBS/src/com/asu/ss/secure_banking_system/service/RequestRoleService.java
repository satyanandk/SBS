package com.asu.ss.secure_banking_system.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.asu.ss.secure_banking_system.model.RequestEntity;
import com.asu.ss.secure_banking_system.model.RoleEntity;
import com.asu.ss.secure_banking_system.model.RoleRequestEntity;
import com.asu.ss.secure_banking_system.model.RoleType;
import com.asu.ss.secure_banking_system.model.SessionFactoryUtil;
import com.asu.ss.secure_banking_system.model.UserEntity;
import com.sbs.model.user.User;


public class RequestRoleService {

	public List<User> getAllInternalUsers(String userID)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String hql = "FROM User UE WHERE UE.roleID >=3 AND userID != '"+userID+"'";
		Query query = session.createQuery(hql);
		List<User> requestList = query.list();
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
	
	public void createRoleRequest(User requestingUser, User user, RoleType role)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		RoleRequestEntity re = new RoleRequestEntity();
		re.setRequestedBy(requestingUser);
		re.setRequestTime(new Date());
		re.setRole(role.getValue());
		re.setUser(user);
		session.save(re);
		session.getTransaction().commit();
		session.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}	
	}
	
	public boolean checkIfRequestAlreadyExists(User requestingUser, User user, RoleType role)
	{
		try{
			SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			String hql = "FROM RoleRequestEntity REE WHERE REE.roleID = :roleID AND REE.";
			session.getTransaction().commit();
			session.close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		return false;
	}
	
	public RoleType[] getAllRoles()
	{
		try{
		
		return RoleType.values();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return null;
		
	}	
	
	public ArrayList<RoleType> getAllRolesForUser(RoleType type){
		
		ArrayList<RoleType> roles = new ArrayList<RoleType>();
		for(RoleType rtype:RoleType.values())
		{
			if(type.getValue()>rtype.getValue())
			{
				roles.add(rtype);
			}
		}
		return roles;
	}
	
	
	
}
