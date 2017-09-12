package com.vehicleman.backend.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vehicleman.backend.entities.User;
import com.vehicleman.backend.util.HibernateUtil;

public class UserDAO {

	private Session session;
	private Transaction tx;

	public UserDAO() {
		session = null;
		tx = null;
	}

	public List<User> getUsers() {

		List<User> users;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("User.get_All");

			users = query.list();
			tx.commit();

		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException e2) {
				e2.printStackTrace();
			}
			throw e;

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return users;
	}

	public User getUser(String id) {
		User user = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("User.get_User_By_Id").setParameter("id", id);
			user = (User) query.uniqueResult();
			tx.commit();

		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException e2) {
				e2.printStackTrace();
			}
			throw e;

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return user;

	}

	public void createUser(User user) {

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();

		} catch (RuntimeException e) {

			try {
				tx.rollback();
			} catch (RuntimeException e2) {
				e2.printStackTrace();
			}

			throw e;

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

		// you have to set all the attributes of the given object to update!

		// TODO: you must give key + attribute you want to modify everything
		// else should remain the same
		// TODO: handle bad request - if id is missing or non existent
		// maybe update only on path /id

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(user);
			tx.commit();

		} catch (RuntimeException e) {

			try {
				tx.rollback();
			} catch (RuntimeException e2) {
				e2.printStackTrace();
			}

			throw e;

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void deleteUser(String id) {

		// TODO: create query to delete instantly. do not fetch first and then
		// delete
		// TODO: handle illegalArgumentException if called on not existing object id

		User user = getUser(id);

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			session.delete(user);

			tx.commit();

		} catch (RuntimeException e) {

			try {
				tx.rollback();
			} catch (RuntimeException e2) {
				e2.printStackTrace();
			}

			throw e;

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
