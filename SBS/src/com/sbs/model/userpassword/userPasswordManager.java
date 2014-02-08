package com.sbs.model.userpassword;


import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.asu.ss.secure_banking_system.model.dbhash;
import com.sbs.model.user.SessionFactoryUtil;

public class userPasswordManager {
	
	private static Session session;
	private static void createSession(){
		session = SessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
	}
	public static UserIDPasswordMappingEntity retrieiveUserinfo(String userID){
		createSession();
		UserIDPasswordMappingEntity userEntity = (UserIDPasswordMappingEntity) session.get(UserIDPasswordMappingEntity.class, userID);
		return userEntity;
	}
	public static int  checkBlock(String userID){
		createSession();
		UserIDPasswordMappingEntity userEntity = retrieiveUserinfo(userID);
		session.close();
		return userEntity.getBlock();
	}
	public static void useBlock(String userID){
		createSession();
		UserIDPasswordMappingEntity userEntity = retrieiveUserinfo(userID);
		userEntity.setBlock(1);
		session.update(userEntity);
		session.getTransaction().commit();
	    session.close();
		
	}
	public static void resetBlock(String userID){
		createSession();
		UserIDPasswordMappingEntity userEntity = retrieiveUserinfo(userID);
		userEntity.setBlock(0);
		session.update(userEntity);
		session.getTransaction().commit();
	    session.close();
		
	}
	public static int checkAnsweredtimes(String userID)
	{
		createSession();
		UserIDPasswordMappingEntity userEntity = retrieiveUserinfo(userID);
		session.close();
		return userEntity.getAnswertimes();
	}
	
	public static void resetAnswerTimes(String userID){
		createSession();
		UserIDPasswordMappingEntity userEntity = retrieiveUserinfo(userID);
		userEntity.setAnswertimes(0);
		session.update(userEntity);
		session.getTransaction().commit();
	    session.close();
	}
	
	public static void increaseAnswerTimes(String userID){
		createSession();
		UserIDPasswordMappingEntity userEntity = retrieiveUserinfo(userID);
		int times = userEntity.getAnswertimes()+1;
		userEntity.setAnswertimes(times);
		session.update(userEntity);
		session.getTransaction().commit();
	    session.close();
	}
	
	public static void saveUserPasswordInfo(String userID, String password)
	{
		createSession();
		UserIDPasswordMappingEntity entity = new UserIDPasswordMappingEntity();
		entity = (UserIDPasswordMappingEntity)session.get(UserIDPasswordMappingEntity.class, userID);
		entity.setPassword(password);
		session.update(entity);
		session.getTransaction().commit();
	    session.close();
	}
	
	public static void createUserPasswordTableInfo(String userID)
	{
		createSession();
		UserIDPasswordMappingEntity entity = new UserIDPasswordMappingEntity();
		entity.setUserID(userID);
		entity.setAnswertimes(0);
		entity.setBlock(0);
		try {
		session.save(entity);
		session.getTransaction().commit();
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
		}
		finally
		{
			session.close();
		}

		
	}
	public static boolean CheckPasswordExistance(String userID){
		createSession();
		UserIDPasswordMappingEntity userEn = userPasswordManager.retrieiveUserinfo(userID);
		
		if(userEn.getPassword()==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String validate(String userID, String password) throws NoSuchAlgorithmException{

		createSession();
		
		String message = null;
		UserIDPasswordMappingEntity userEn = userPasswordManager.retrieiveUserinfo(userID);
		if(userEn.getPassword()==null)
		{
			return message="register";
		}
			
		String correctPassword = userEn.getPassword();
		password= dbhash.hash(password);
		int isBlocked =  checkBlock(userID);
		int times = checkAnsweredtimes(userID);
		if(password.equals(correctPassword) == true && isBlocked == 0 && times<4 ){
			resetAnswerTimes(userID);
			message=null;
		}
		else if(isBlocked == 1){
			message = "b";
			session.close();
		}
		else if(password.equals(correctPassword) == false && times<4){
			increaseAnswerTimes(userID);
			
			message = "p";
		}	
		else if(password.equals(correctPassword) == false && times>3){
			resetAnswerTimes(userID);
			useBlock(userID);
			return message = "b";
		}
		else{}
		return message;
	}
}
