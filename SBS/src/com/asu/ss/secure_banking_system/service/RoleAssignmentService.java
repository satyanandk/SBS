package com.asu.ss.secure_banking_system.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.asu.ss.secure_banking_system.model.RequestEntity;
import com.asu.ss.secure_banking_system.model.RoleRequestEntity;
import com.asu.ss.secure_banking_system.model.RoleType;
import com.asu.ss.secure_banking_system.model.SessionFactoryUtil;
import com.asu.ss.secure_banking_system.util.AuthorizationUtil;
import com.sbs.model.transaction.Transaction;
import com.sbs.model.transaction.TransactionManager;
import com.sbs.model.transaction.TransactionType;
import com.sbs.model.user.User;

public class RoleAssignmentService {

	
	public boolean checkUserHierarchy(User assigned, User assignee, int roleID)
	{
		
		if(roleID == RoleType.CORPORATE_LEVEL_OFFICER.getValue())
		{
			return false;
		}
		else if(assignee.getRoleID() == RoleType.CORPORATE_LEVEL_OFFICER.getValue()
				&& ( assignee.getRoleID() == RoleType.SYSTEM_ADMIN.getValue()
					|| assignee.getRoleID() == RoleType.REGULAR_EMPLOYEE.getValue()
					|| assignee.getRoleID() == RoleType.MANAGER.getValue()
					|| assignee.getRoleID() == RoleType.EXTERNAL_MERCHANT_USER.getValue()
					|| assignee.getRoleID() == RoleType.EXTERNAL_REGULAR_USER.getValue()
					))
		{
			return true;
		}
		else if(assignee.getRoleID()==RoleType.MANAGER.getValue()
				&& (   assigned.getRoleID() == RoleType.SYSTEM_ADMIN.getValue()
					|| assigned.getRoleID() == RoleType.REGULAR_EMPLOYEE.getValue()
					|| assigned.getRoleID() == RoleType.EXTERNAL_MERCHANT_USER.getValue()
					|| assigned.getRoleID() == RoleType.EXTERNAL_REGULAR_USER.getValue()))
		{
			return true;
		}
		else if(assignee.getRoleID() == RoleType.REGULAR_EMPLOYEE.getValue()
				&& (  assigned.getRoleID() == RoleType.EXTERNAL_MERCHANT_USER.getValue()
					|| assigned.getRoleID() == RoleType.EXTERNAL_REGULAR_USER.getValue()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public RoleRequestEntity getRoleRequestFromID(String id)
	{
		try
		{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		RoleRequestEntity re = (RoleRequestEntity)session.get(RoleRequestEntity.class, (int)(Integer.parseInt(id)));
		session.getTransaction().commit();
		session.close();
		return re;
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}		
		return null;
	}
	public boolean checkIfMultipleAuthorizationSatisfied(User assignee, List<RequestEntity> coAssignee)
	{
		int similarLevelCoAssignee = 0;
		
		for(int i=0; i<coAssignee.size();i++)
		{
			if(assignee.getRoleID()==coAssignee.get(i).getRequestedBy().getRoleID())
			{
				similarLevelCoAssignee++;
			}
		}
		if(similarLevelCoAssignee>=2)
			return true;
		else
			return false;
		
	}
	
	private void updateRoleRequestEntity(RoleRequestEntity ree)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(ree);
		//role transaction 2
		session.getTransaction().commit();
		session.close();
				}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
	}
	
	public void assignRoleToUser(RoleRequestEntity re)
	{
		try{
			if(re.isValidated()==true)
			{
				SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				re.getRequestForUser().setRoleID(re.getRole());
				re.setAssigned(true);
				re.setValidated(true);
				session.update(re.getRequestForUser());
				session.update(re);
				AuthorizationUtil.updateAuthorizationForUser(re.getRequestForUser(), AuthorizationUtil.getEnumValueOfRole(re.getRole()));
				Transaction t = new Transaction(TransactionType.USR_ROLE_CHANGED,
						null, null, re.getRequestedBy().getUserID(), re
								.getRequestForUser().getUserID(), 0.0, 0.0,
						"User role changed to " + AuthorizationUtil.getStringValueOfRole(re.getRole()));
				TransactionManager.createTransaction(t);
				session.getTransaction().commit();
				session.close();
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public boolean isDuplicateRequest(String requestedBy, String requestFor,  int role)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM RoleRequestEntity RE WHERE RE.isValidated = false AND RE.requestForUser = '"
				+requestFor+"' AND RE.roleID = "+role
				+" AND RE.requestedBy = '"+requestedBy+"'";
		Query query = session.createQuery(hql);
		List<RequestEntity> requestList = query.list();		
		
		if(requestList.size()>=1)
			return true;
		session.getTransaction().commit();
		session.close();
		
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return false;		
	}
	
	public boolean isValidRequest(RoleRequestEntity re)
	{
		try{
			boolean hierarchyValidation = false;
			boolean multipleValidation = false;
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM RoleRequestEntity RE WHERE RE.isValidated = 'false' AND RE.requestForUser = '"
				+re.getRequestForUser().getUserID()+"' AND RE.roleID = "+re.getRole()
				+" AND RE.requestedBy != '"+re.getRequestedBy().getUserID()+"'";
		Query query = session.createQuery(hql);
		List<RequestEntity> requestList = query.list();		
		User requestingUser = (User)(session.get(User.class, re.getRequestedBy().getUserID()));
		User requestforUser = (User)(session.get(User.class, re.getRequestForUser().getUserID()));
		//check if the request came from higher level user than the user to be assigned
		hierarchyValidation = checkUserHierarchy(requestforUser, requestingUser, re.getRole());
		if(hierarchyValidation)
		{
			re.setValidated(true);
			updateRoleRequestEntity(re);
		}
		else
		{
			multipleValidation = checkIfMultipleAuthorizationSatisfied(re.getRequestedBy(), requestList);
			if(multipleValidation)
			{
				re.setValidated(true);
				
				for(int i=0; i<requestList.size();i++)
				{
					((RoleRequestEntity)requestList.get(i)).setValidated(true);
					updateRoleRequestEntity(((RoleRequestEntity)requestList.get(i)));
				}
			}
		}
		session.getTransaction().commit();
		session.close();
		return hierarchyValidation|multipleValidation;
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return false;
	}
}
