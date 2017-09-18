package com.vehicleman.backend.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {

			SessionFactory sessionFactory = new Configuration().configure("\\com\\vehicleman\\backend\\util\\hibernate.cfg.xml").buildSessionFactory();

			return sessionFactory;

			//			Configuration configuration = new Configuration();
			//			configuration.configure("\\com\\vehicleman\\backend\\util\\hibernate.cfg.xml");
			//			System.out.println("Hibernate annotation configuration loaded");
			//
			//			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			//
			//			System.out.println("Hibernate Annotation serviceRegistry created");
			//
			//			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			//
			//			return sessionFactory;

		} catch(Throwable ex) {

			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

}