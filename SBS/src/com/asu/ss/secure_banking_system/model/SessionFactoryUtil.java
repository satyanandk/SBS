package com.asu.ss.secure_banking_system.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SessionFactoryUtil {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	/* making constructor private, so that no one can create an object */
	private SessionFactoryUtil()
	{
		
	}
	static {
		try {
		Configuration configuration = new Configuration();
		configuration.configure("/com/sbs/configuration/hibernate.cfg.xml");
		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		catch(Throwable ex)
		{
			System.err.println("Session Factory Creation Failed."+ex.getMessage());
			ex.printStackTrace();
			
		}
	}
	
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
}
