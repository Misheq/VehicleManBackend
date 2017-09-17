package com.vehicleman.backend.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.util.HibernateUtil;

public class VehicleDAO {

	private Session session;
	private Transaction tx;

	public VehicleDAO() {
		session = null;
		tx = null;
	}

	public List<Vehicle> getVehicles() {

		List<Vehicle> vehicles;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("Vehicle.get_All");

			vehicles = query.list();
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

		return vehicles;
	}

	public Vehicle getVehicle(String id) {
		Vehicle vehicle = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("Vehicle.get_Vehicle_By_Id").setParameter("id", id);
			vehicle = (Vehicle) query.uniqueResult();
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

		return vehicle;

	}

	public void createVehicle(Vehicle vehicle) {

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(vehicle);
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

	public void updateVehicle(Vehicle vehicle) {

		// you have to set all the attributes of the given object to update!

		// TODO: you must give key + attribute you want to modify everything
		// else should remain the same
		// TODO: handle bad request - if id is missing or non existent
		// maybe update only on path /id

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(vehicle);
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

	public void deleteVehicle(String id) {

		// TODO: create query to delete instantly. do not fetch first and then
		// delete
		// TODO: handle illegalArgumentException if called on not existing object id

		Vehicle vehicle = getVehicle(id);

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			session.delete(vehicle);

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
