package com.sbs.model.notification;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sbs.model.user.SessionFactoryUtil;

public class NotificationManager {
	private static Session session;

	private static void createSession() {
		session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
	}

	public static void createNotification(Notification n) {
		createSession();
		session.save(n);
		session.getTransaction().commit();
		session.close();
	}
	
	public static void updateNotification(Notification n) {
		//n.setApprovedDate(new Date());
		createSession();
		String hql = "from Notification n where notificationId = :nid";
		Query query = session.createQuery(hql);
		query.setParameter("nid", n.getNotificationId());
		List<Notification> nList = query.list();
		nList.get(0).setApprovedDate(new Date());
		nList.get(0).setStatus(n.getStatus());
		session.getTransaction().commit();
		session.close();
	}

	public static List<Notification> getNotifications(String userId) {
		createSession();
		String hql = "from Notification n where userId = :userId and status = 'R'";
		Query query = session.createQuery(hql);
		query.setString("userId", userId);
		List<Notification> list = query.list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
}
