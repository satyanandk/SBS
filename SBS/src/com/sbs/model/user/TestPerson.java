package com.sbs.model.user;
 
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class TestPerson {
	public static void main(String[] args) {
		
		createPerson();
		queryPerson();
	}

	private static void queryPerson() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User");                
		List <User>list = query.list();
		java.util.Iterator<User> iter = list.iterator();
		while (iter.hasNext()) {
			User user = iter.next();
			System.out.println("User: \""  +"\", "+user.getUserID() +"\", "+user.getLastname() + "\", "+user.getFirstname());
		}
		session.getTransaction().commit();
		session.close();
	}

	public static void createPerson() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		User user = new User();
		user.setUserID("qingyun");
		user.setLastname("li");
		user.setFirstname("tom");
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
}