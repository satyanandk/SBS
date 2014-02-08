package com.sbs.model.crypto;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sbs.model.user.SessionFactoryUtil;
import com.sbs.model.user.User;

public class UserPkiHibernateManager {

	private static Session session;
	private static void createSession(){
		session = SessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
	}
	
	public static void saveUserPki(UserPki u) {
		createSession();
		session.save(u);
		session.getTransaction().commit();
		session.close();
	}
	
	public static UserPki getUserPki(String userId) {
		createSession();
		UserPki userPki = null;
		String hql = "from UserPki as userpki where userpki.userId=:userId";
		Query query = session.createQuery(hql);
		query.setString("userId", userId);
		List <UserPki> list = query.list();
		if (list.size() == 1) {
			userPki = list.get(0);
		} else {
			System.out.println("Error getting User PKI information");
		}
		session.getTransaction().commit();
		session.close();
		return userPki;		
	}
}
