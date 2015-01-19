package com.mulodo.test.dao;

import java.util.List;

import com.mulodo.test.exception.DAOException;
import com.mulodo.test.model.Person;
 
public interface PersonDAO {
 
    public void addPerson(Person p) throws DAOException;
    public void updatePerson(Person p) throws DAOException;
    public List<Person> listPersons() throws DAOException;
    public Person getPersonById(int id) throws DAOException;
    public Person getPersonByName(String name) throws DAOException;
    public void removePerson(int id) throws DAOException;
    
}