package com.vehicleman.backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.vehicleman.backend.entities.User;
import com.vehicleman.backend.util.HibernateUtil;

public class UserDAO {

	private Session session;

	public UserDAO() {

	}

	public List<User> getUsers() {
		
		session = null;
		List<User> users = new ArrayList<>();

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("User.get_All");
			users = query.list();
			
			session.getTransaction().commit();

		} catch (Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return users;
	}

	public User getUser(String id) {
		session = null;
		User user = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("User.get_User_By_Id").setParameter("id", id);
			user = (User) query.uniqueResult();
			
			session.getTransaction().commit();

		} catch (Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return user;

	}

	public void createUser(User user) {
		session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.save(user);
			
			session.getTransaction().commit();

		} catch (Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}

		// handle MySQLIntegrityConstraintViolationException - > create entity
		// with same key (if key will be auto generated, then it is not
		// necessary to be given explicitly)
	}

	public void updateUser(User user) {
		session = null;
		
		// you have to set all the attributes of the given object to update!

		// TODO: you must give key + attribute you want to modify everything
		// else should remain the same
		// TODO: handle bad request - if id is missing or non existent
		// maybe update only on path /id

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.update(user);
			
			session.getTransaction().commit();

		} catch (Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void deleteUser(String id) {
		session = null;
		
		// TODO: create query to delete instantly. do not fetch first and then
		// delete
		// TODO: handle illegalArgumentException if called on not existing object id

		User user = getUser(id);

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			session.delete(user);

			session.getTransaction().commit();

		} catch (Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
