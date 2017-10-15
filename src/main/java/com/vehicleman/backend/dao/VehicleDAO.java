package com.vehicleman.backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.util.HibernateUtil;

public class VehicleDAO {

	private Session session;

	public VehicleDAO() {

	}

	public List<Vehicle> getVehicles() {
		session = null;
		List<Vehicle> vehicles = new ArrayList<>();

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("Vehicle.get_All");
			vehicles = query.list();

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

		return vehicles;
	}

	public Vehicle getVehicle(int id) {
		session = null;
		Vehicle vehicle = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("Vehicle.get_Vehicle_By_Id").setParameter("id", id);
			vehicle = (Vehicle) query.uniqueResult();

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

		return vehicle;

	}

	public Vehicle getVehicleByRegistrationNumber(String registrationNumber) {
		session = null;
		Vehicle vehicle = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("Vehicle.get_Vehicle_By_Registration_Number")
					.setParameter("registrationNumber", registrationNumber);
			vehicle = (Vehicle) query.uniqueResult();

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

		return vehicle;
	}

	public void createVehicle(Vehicle vehicle) {
		session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			session.save(vehicle);

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

		// handle MySQLIntegrityConstraintViolationException - > create entity
		// with same key (if key will be auto generated, then it is not
		// necessary to be given explicitly)
	}

	public void updateVehicle(Vehicle vehicle) {
		session = null;

		// you have to set all the attributes of the given object to update!

		// TODO: you must give key + attribute you want to modify everything
		// else should remain the same

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			session.update(vehicle);

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

	public void deleteVehicle(int id) {
		session = null;

		// TODO: create query to delete instantly. do not fetch first and then
		// delete
		// TODO: handle illegalArgumentException if called on not existing object id

		Vehicle vehicle = getVehicle(id);

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			session.delete(vehicle);

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
