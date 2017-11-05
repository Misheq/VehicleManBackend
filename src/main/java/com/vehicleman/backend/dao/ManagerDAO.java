package com.vehicleman.backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import com.vehicleman.backend.entities.Manager;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.Vehicle;
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

		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return managers;
	}

	public List<Person> getManagerPersons(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<Person> persons = new ArrayList<>();

		try {
			transaction = session.beginTransaction();

			Query query = session.getNamedQuery("Manager.get_Manager_Persons").setParameter("id", id);
			persons = query.list();

			transaction.commit();

		} catch (Exception e) {
			if (session != null) {
				transaction.rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return persons;
	}

	public List<Vehicle> getManagerVehicles(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<Vehicle> vehicles = new ArrayList<>();

		try {
			transaction = session.beginTransaction();

			Query query = session.getNamedQuery("Manager.get_Manager_Vehicles").setParameter("id", id);
			vehicles = query.list();

			transaction.commit();

		} catch (Exception e) {
			if (session != null) {
				transaction.rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return vehicles;
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

		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return manager;

	}

	public Manager findManagerByEmail(String email) {
		session = null;
		Manager manager = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("Manager.get_Manager_By_Email").setParameter("email", email);
			manager = (Manager) query.uniqueResult();

			session.getTransaction().commit();

		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return manager;
	}

	public Manager loginManager(String email, String password) {
		Manager manager = findManagerByEmail(email);
		if (manager != null) {
			if (BCrypt.checkpw(password, manager.getPassword())) {
				return manager;
			}
		}

		return null;
	}

	public Manager createManager(Manager manager) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.save(manager);
			transaction.commit();

		} catch (Exception e) {
			if (session != null) {
				transaction.rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return manager;
	}

	public void updateManager(Manager manager) {
		session = null;

		// you have to set all the attributes of the given object to update! - handle maybe on frontend

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			session.update(manager);

			session.getTransaction().commit();

		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		} finally {
			if (session != null) {
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

		} catch (Exception e) {
			if (session != null) {
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
