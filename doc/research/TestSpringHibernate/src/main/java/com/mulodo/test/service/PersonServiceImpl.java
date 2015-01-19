package com.mulodo.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.test.dao.PersonDAO;
import com.mulodo.test.exception.DAOException;
import com.mulodo.test.model.Person;


@Service
public class PersonServiceImpl implements PersonService {
	
	private PersonDAO personDAO;
	
	public void setPersonDAO(PersonDAO personDAO){
		this.personDAO = personDAO;
	}
	
	public void addPerson(Person p) throws DAOException {
		this.personDAO.addPerson(p);
	}

	public void updatePerson(Person p) throws DAOException {
		this.personDAO.updatePerson(p);
	}

	@Transactional
	public List<Person> listPersons() throws DAOException {
		return personDAO.listPersons();
	}

	@Transactional
	public Person getPersonById(int id) throws DAOException {
		return this.personDAO.getPersonById(id);
	}
	
	@Transactional
	public Person getPersonByName(String name) throws DAOException {
		return this.personDAO.getPersonByName(name);
	}

	public void removePerson(int id) throws DAOException {
		this.personDAO.removePerson(id);
		
	}
	
}
