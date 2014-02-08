package com.asu.ss.secure_banking_system.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.asu.ss.secure_banking_system.model.DepartmentEnum;
import com.asu.ss.secure_banking_system.model.PageAuthorizationEntity;
import com.asu.ss.secure_banking_system.model.RoleType;
import com.asu.ss.secure_banking_system.model.SessionFactoryUtil;
import com.sbs.model.user.User;

public class AuthorizationUtil {

	
	public static void main(String[]args)
	{
		User user = new User();
		user.setUserID("sandip");
		PageAuthorizationEntity pe = new PageAuthorizationEntity();
		pe.setPageAuthorizationID(6);
		user.setPageAuthorization(pe);
		//AuthorizationUtil.createAuthorizationForUser(user, RoleType.EXTERNAL_MERCHANT_USER);
		AuthorizationUtil.updateAuthorizationForUser(user, RoleType.EXTERNAL_MERCHANT_USER);

		user.setUserID("satya");
		AuthorizationUtil.createAuthorizationForUser(user, RoleType.MANAGER);

	}
	
	public static RoleType getEnumValueOfRole(int role)
	{
		if(RoleType.EXTERNAL_REGULAR_USER.getValue()==role)
				return RoleType.EXTERNAL_REGULAR_USER;
		
		else if(RoleType.SYSTEM_ADMIN.getValue()==role)
				return RoleType.SYSTEM_ADMIN;
			
		else if(RoleType.REGULAR_EMPLOYEE.getValue()==role)
				return RoleType.REGULAR_EMPLOYEE;
				
		else if(RoleType.MANAGER.getValue()==role)
				return RoleType.MANAGER;
			
		else if(RoleType.CORPORATE_LEVEL_OFFICER.getValue()==role)	
				return RoleType.CORPORATE_LEVEL_OFFICER;
				
		else if(RoleType.EXTERNAL_MERCHANT_USER.getValue()==role)
				return RoleType.EXTERNAL_MERCHANT_USER;
		else
				return null;
		}
	
	public static String getStringValueOfRole(int role)
	{
		if(RoleType.EXTERNAL_REGULAR_USER.getValue()==role)
				return "External user (regular)";
		
		else if(RoleType.SYSTEM_ADMIN.getValue()==role)
				return "System admin";
			
		else if(RoleType.REGULAR_EMPLOYEE.getValue()==role)
				return "Regular employee";
				
		else if(RoleType.MANAGER.getValue()==role)
				return "Manager";
			
		else if(RoleType.CORPORATE_LEVEL_OFFICER.getValue()==role)	
				return "Corporate level officer";
				
		else if(RoleType.EXTERNAL_MERCHANT_USER.getValue()==role)
				return "External user (Merchant)";
		else
				return "";
		}
	
	public static String getStringValueOfDepartment(int role)
	{
		if(DepartmentEnum.CUSTOMER.getValue()==role)
				return "Customer";
		
		else if(DepartmentEnum.ITANDTECHICALSUPPORT.getValue()==role)
				return "IT and Tech Department";
			
		else if(DepartmentEnum.HUMANRESOURCE.getValue()==role)
				return "Human Resource";
				
		else if(DepartmentEnum.COMPANYMANAGEMENT.getValue()==role)
				return "Management Dapartment";
			
		else if(DepartmentEnum.SALES.getValue()==role)	
				return "Sales Department";
				
		else if(DepartmentEnum.TRANSACTION.getValue()==role)
				return "Transaction Department";
		else
				return "";
		}
	
	public static boolean checkIfInternalUser(String userID)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User user = (User)session.get(User.class, userID);
		session.getTransaction().commit();
		session.close();		
		if(user==null)
			return false;
		else if(user.getRoleID()==RoleType.REGULAR_EMPLOYEE.getValue())
			return true;
		else 
			return false;

		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return false;
	}
	
	public static boolean checkIfExternalUser(String userID)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User user = (User)session.get(User.class, userID);
		session.getTransaction().commit();
		session.close();		
		if(user==null)
			return false;
		else if(user.getRoleID()==RoleType.EXTERNAL_MERCHANT_USER.getValue()
				|| user.getRoleID()==RoleType.EXTERNAL_REGULAR_USER.getValue())
			return true;
		else 
			return false;

		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return false;		
	}
	public static User loadAuthorization(User user)
	{
		try{
			
			PageAuthorizationEntity pe = null;
			SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			String hql = "FROM PageAuthorizationEntity PE WHERE PE.user = :user";
			Query query = session.createQuery(hql);
			query.setParameter("user", user);
			List<PageAuthorizationEntity> authorizationList = query.list();
			pe = authorizationList.get(0);
			user.setPageAuthorization(pe);
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception exception)
		{
			
		}
		
		return user;
	}
	

	public static boolean updateAuthorizationForUser(User user, RoleType roleType)
	{
		try{
			
		int id = 0;	
		
		PageAuthorizationEntity pe = loadAuthorization(user).getPageAuthorization();
		
		
		id = pe.getPageAuthorizationID();//store id
		pe = new PageAuthorizationEntity();//just to clean all authorization
		pe.setPageAuthorizationID(id);
		pe.setUser(user);
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		switch(roleType)
		{

		
			case SYSTEM_ADMIN:
			
				pe.setAdminNotificationAuthorized(true);
				pe.setWelcomeAdminAuthorized(true);
				pe.setTAANotificationAuthorized(true);
				pe.setAssignRoleAuthorized(true);
				pe.setAssignTAAAuthorized(true);
				pe.setSystemLogAuthorized(true);
				pe.setLoginAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setNotificationAuthorized(true);
				session.update(pe);
				user.setPageAuthorization(pe);
				break;
			
			case REGULAR_EMPLOYEE:
				pe.setRequestRoleAuthorized(true);
				pe.setProcessTAAAuthorized(true);
				pe.setTAANotificationAuthorized(true);
				pe.setAddUserAuthorized(true);
				pe.setDeleteUserPageAuthorized(true);
				pe.setTransferUserAuthorized(true);
				pe.setLoginAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setCreateAccountAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setInternalTransactionAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setNotificationAuthorized(true);
				session.update(pe);
				
				user.setPageAuthorization(pe);
				break;
			
			case MANAGER:
				pe.setFinancialOTPAuthorized(true);
				user.setPageAuthorization(pe);
				pe.setProcessTAAAuthorized(true);
				pe.setRequestRoleAuthorized(true);
				pe.setAddUserAuthorized(true);
				pe.setDeleteUserPageAuthorized(true);
				pe.setTransferUserAuthorized(true);
				pe.setLoginAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setCreateAccountAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setInternalTransactionAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setNotificationAuthorized(true);
				session.update(pe);
				break;
			
			case CORPORATE_LEVEL_OFFICER:	
				pe.setFinancialOTPAuthorized(true);
				user.setPageAuthorization(pe);
				pe.setProcessTAAAuthorized(true);
				pe.setRequestRoleAuthorized(true);
				pe.setAddUserAuthorized(true);
				pe.setDeleteUserPageAuthorized(true);
				pe.setTransferUserAuthorized(true);
				pe.setLoginAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setCreateAccountAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setInternalTransactionAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setNotificationAuthorized(true);
				session.update(pe);
				break;
				
			case EXTERNAL_REGULAR_USER:
				pe.setCreditAuthorized(true);
				pe.setDebitAuthorized(true);
				pe.setTransferAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setPaymentConfAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setRequestTAAAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setExternalTransactionAuthorized(true);
				pe.setNotificationAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				user.setPageAuthorization(pe);
				
				
				session.update(pe);
				break;
				
			case EXTERNAL_MERCHANT_USER:
				pe.setFinancialOTPAuthorized(true);
				pe.setCreditAuthorized(true);
				pe.setDebitAuthorized(true);
				pe.setTransferAuthorized(true);
				pe.setPaymentConfAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setRequestPaymentAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setExternalTransactionAuthorized(true);
				pe.setNotificationAuthorized(true);
				pe.setRequestTAAAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				
				session.update(pe);
				user.setPageAuthorization(pe);
				break;
			
			default:
			
				break;
		}
		session.getTransaction().commit();
		session.close();
		return true;
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return false;
	}
	
	
	public static boolean createAuthorizationForUser(User user, RoleType roleType)
	{
		try{
			
		PageAuthorizationEntity pe = new PageAuthorizationEntity();
		pe.setUser(user);
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		switch(roleType)
		{

		
			case SYSTEM_ADMIN:
			
				pe.setAdminNotificationAuthorized(true);
				pe.setWelcomeAdminAuthorized(true);
				pe.setTAANotificationAuthorized(true);
				pe.setAssignRoleAuthorized(true);
				pe.setAssignTAAAuthorized(true);
				pe.setSystemLogAuthorized(true);
				pe.setLoginAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setNotificationAuthorized(true);
				pe.setContactChangeAuthorized(true);
				session.save(pe);
				user.setPageAuthorization(pe);
				break;
			
			case REGULAR_EMPLOYEE:
				pe.setRequestRoleAuthorized(true);
				pe.setProcessTAAAuthorized(true);
				pe.setTAANotificationAuthorized(true);
				pe.setAddUserAuthorized(true);
				pe.setDeleteUserPageAuthorized(true);
				pe.setTransferUserAuthorized(true);
				pe.setLoginAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setCreateAccountAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setInternalTransactionAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setNotificationAuthorized(true);
				
				session.save(pe);
				
				user.setPageAuthorization(pe);
				break;
			
			case MANAGER:
				pe.setFinancialOTPAuthorized(true);
				pe.setRequestRoleAuthorized(true);
				pe.setProcessTAAAuthorized(true);
				pe.setAddUserAuthorized(true);
				pe.setDeleteUserPageAuthorized(true);
				pe.setTransferUserAuthorized(true);
				pe.setLoginAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setCreateAccountAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setInternalTransactionAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setNotificationAuthorized(true);
				session.save(pe);
				user.setPageAuthorization(pe);
				break;
			
			case CORPORATE_LEVEL_OFFICER:	
				pe.setFinancialOTPAuthorized(true);
				user.setPageAuthorization(pe);
				pe.setRequestRoleAuthorized(true);
				pe.setProcessTAAAuthorized(true);
				pe.setAddUserAuthorized(true);
				pe.setDeleteUserPageAuthorized(true);
				pe.setTransferUserAuthorized(true);
				pe.setLoginAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setCreateAccountAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setInternalTransactionAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setNotificationAuthorized(true);
				session.save(pe);
				break;
				
			case EXTERNAL_REGULAR_USER:
				pe.setCreditAuthorized(true);
				pe.setDebitAuthorized(true);
				pe.setTransferAuthorized(true);
				pe.setFinancialOTPAuthorized(true);
				pe.setPaymentConfAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setRequestTAAAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setNotificationAuthorized(true);
				pe.setExternalTransactionAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setLoginAuthorized(true);
				user.setPageAuthorization(pe);
				
				session.save(pe);
				break;
				
			case EXTERNAL_MERCHANT_USER:
				pe.setFinancialOTPAuthorized(true);
				pe.setCreditAuthorized(true);
				pe.setDebitAuthorized(true);
				pe.setTransferAuthorized(true);
				pe.setPaymentConfAuthorized(true);
				pe.setResultPageAuthorized(true);
				pe.setRequestPaymentAuthorized(true);
				pe.setProfilePageAuthorized(true);
				pe.setNotificationAuthorized(true);
				pe.setExternalTransactionAuthorized(true);
				pe.setRequestTAAAuthorized(true);
				pe.setForgetPasswordAuthorized(true);
				pe.setNewPasswordSetupAuthorized(true);
				pe.setContactChangeAuthorized(true);
				pe.setLoginAuthorized(true);
				user.setPageAuthorization(pe);
				session.save(pe);
				
				break;
			
			default:
			
				break;
			
			
		}
		session.getTransaction().commit();
		session.close();
		
		}
		catch(Exception exception)
		{
		
		}
		return true;
	}
	
	public static boolean updateOrDeleteAuthorization(User user)
	{
		try{
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();		
		session.update(user.getPageAuthorization());
		session.getTransaction().commit();
		session.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return true;
	}
	
	
	
}
