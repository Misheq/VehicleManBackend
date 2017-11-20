package com.vehicleman.backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.utils.HibernateUtil;

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
	}

	public void updateVehicle(Vehicle vehicle) {
		session = null;

		// you have to set all the attributes of the given object to update!

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
