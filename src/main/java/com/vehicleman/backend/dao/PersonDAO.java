package com.vehicleman.backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.util.HibernateUtil;

public class PersonDAO {
	
	Session session;
	
	public PersonDAO() {
		
	}
	
	public List<Person> getPersons() {
		
		session = null;
		List<Person> persons = new ArrayList<>();

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("Person.get_All");
			persons = query.list();
			
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

		return persons;
	}

	public Person getPerson(int id) {
		
		Person person = null;
		session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("Person.get_Person_By_Id").setParameter("id", id);
			person = (Person) query.uniqueResult();
			
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

		return person;

	}

	public void createPerson(Person person) {

		session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.save(person);
			
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

	public void updatePerson(Person person) {

		session = null;
		
		// you have to set all the attributes of the given object to update!

		// TODO: you must give key + attribute you want to modify everything
		// else should remain the same
		// TODO: handle bad request - if id is missing or non existent
		// maybe update only on path /id

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.update(person);
			
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

	public void deletePerson(int id) {

		// TODO: create query to delete instantly. do not fetch first and then
		// delete
		// TODO: handle illegalArgumentException if called on not existing object id
		
		session = null;
		Person person = getPerson(id);

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			session.delete(person);

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
	
	private boolean containsPerson(Person person) {
		List<Person> persons = getPersons();
		for (Person p : persons) {
			if (p.getPersonId() == person.getPersonId()) {
				return true;
			}
		}
		return false;
	}
}
