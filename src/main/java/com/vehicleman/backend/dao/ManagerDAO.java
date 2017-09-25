package com.vehicleman.backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.vehicleman.backend.entities.Manager;
import com.vehicleman.backend.util.HibernateUtil;

public class ManagerDAO {

	private Session session;

	public ManagerDAO() {

	}

	public List<Manager> getManagers() {

		session = null;
		List<Manager> managers = new ArrayList<>();

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("Manager.get_All");
			managers = query.list();

			session.getTransaction().commit();

		} catch(Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if(session != null) {
				session.close();
			}
		}

		return managers;
	}

	public Manager getManager(int id) {
		session = null;
		Manager manager = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("Manager.get_Manager_By_Id").setParameter("id", id);
			manager = (Manager) query.uniqueResult();

			session.getTransaction().commit();

		} catch(Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if(session != null) {
				session.close();
			}
		}

		return manager;

	}

	public void createManager(Manager manager) {
		session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			session.save(manager);

			session.getTransaction().commit();

		} catch(Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if(session != null) {
				session.close();
			}
		}

		// handle MySQLIntegrityConstraintViolationException - > create entity
		// with same key (if key will be auto generated, then it is not
		// necessary to be given explicitly)
	}

	public void updateManager(Manager manager) {
		session = null;

		// you have to set all the attributes of the given object to update! - handle maybe on frontend

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			session.update(manager);

			session.getTransaction().commit();

		} catch(Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	public void deleteManager(int id) {
		session = null;

		// TODO: create query to delete instantly. do not fetch first and then
		// delete

		Manager manager = getManager(id);

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			session.delete(manager);

			session.getTransaction().commit();

		} catch(Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
}
