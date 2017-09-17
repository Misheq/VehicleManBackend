package com.vehicleman.backend.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.util.HibernateUtil;

public class PersonDAO {

	private Session session;
	private Transaction tx;

	public PersonDAO() {
		session = null;
		tx = null;
	}

	public List<Person> getPersons() {

		List<Person> persons;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("Person.get_All");

			persons = query.list();
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

		return persons;
	}

	public Person getPerson(String id) {
		Person person = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("Person.get_Person_By_Id").setParameter("id", id);
			person = (Person) query.uniqueResult();
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

		return person;

	}

	public void createPerson(Person person) {

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(person);
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

	public void updatePerson(Person person) {

		// you have to set all the attributes of the given object to update!

		// TODO: you must give key + attribute you want to modify everything
		// else should remain the same
		// TODO: handle bad request - if id is missing or non existent
		// maybe update only on path /id

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(person);
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

	public void deletePerson(String id) {

		// TODO: create query to delete instantly. do not fetch first and then
		// delete
		// TODO: handle illegalArgumentException if called on not existing object id

		Person person = getPerson(id);

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			session.delete(person);

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
