package com.mulodo.test.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mulodo.test.constraints.Constraints;
import com.mulodo.test.exception.DAOException;
import com.mulodo.test.model.Person;

@Repository
public class PersonDAOImpl implements PersonDAO {

	private static final Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);
	private SessionFactory sessionFactory;
	private Session session = null;
	private Transaction tx = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	

    public void addPerson(Person p) throws DAOException {
    	try{
	        session = this.sessionFactory.openSession();
	        tx = session.beginTransaction();
	        session.save(p);
	        tx.commit();
        logger.info("Person saved successfully, Person Details="+p);
    	}catch(Exception ex){
    		if(tx != null)	tx.rollback();
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
    	}
    }
 

    public void updatePerson(Person p) throws DAOException {
    	System.out.println("id = "+ p.getId());
		System.out.println("name = "+ p.getName());
		System.out.println("Country = "+ p.getCountry());
        try{
	        session = this.sessionFactory.openSession();
	        tx = session.beginTransaction();
	        session.update(p);
	        tx.commit();
	        System.out.println("success");
	        logger.info("Person updated successfully, Person Details="+p);
    	}catch(Exception ex){
    		ex.printStackTrace();
    		tx.rollback();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
    	}
    }
 
    @SuppressWarnings("unchecked")
    public List<Person> listPersons(int id, String name, String country, int pageNum) throws DAOException {
        try{
	        session = this.sessionFactory.openSession();
	        Criteria criteria = session.createCriteria(Person.class);
	        if(id != 0){
	        	criteria.add(Restrictions.eq("id",id));
	        }
	        if(name != null){
	        	criteria.add(Restrictions.eq("name",name));
	        }
	        if(country != null){
	        	criteria.add(Restrictions.eq("country",country));
	        }
	        
	        criteria.setFirstResult((pageNum - 1)* Constraints.LIMIT_ROW);
	        criteria.setMaxResults(Constraints.LIMIT_ROW);
	        
	        
	        List<Person> personsList = criteria.list();
	        for(Person p : personsList){
	            logger.info("Person List::"+p);
	        }
	        return personsList;
    	}catch(Exception ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
    	}
    }
 
    
    public Person getPersonById(int id) throws DAOException {  
        try{
	        session = this.sessionFactory.openSession();
	        Person p = (Person) session.get(Person.class, new Integer(id));
	        logger.info("Person loaded successfully, Person details="+p);
	        return p;
    	}catch(ObjectNotFoundException ex){
    		ex.printStackTrace();
    	}catch(Exception ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
    	}
		return null;
    }
 

	public Person getPersonByName(String name) throws DAOException {    
     
        try{
	        session = this.sessionFactory.openSession();
	        Criteria criteria = session.createCriteria(Person.class);
	        
	        if(name != null){
	        	criteria.add(Restrictions.eq("name",name));
	        }
	        
	        
	        Person p = (Person) criteria.list().get(0);
	        
	        logger.info("Person loaded successfully, Person details="+p);
	        return p;
    	}catch(ObjectNotFoundException ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}catch(Exception ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
    	}
    }
   
    public void removePerson(int id) throws DAOException {
        try{
	        session = this.sessionFactory.openSession();
	        tx = session.beginTransaction();
	        Person p = (Person) session.load(Person.class, new Integer(id));
	        if(null != p){
	            session.delete(p);
	        }
	        System.out.println("delete success ");
	        tx.commit();
	        logger.info("Person deleted successfully, person details="+p);
    	}catch(Exception ex){
    		if(tx != null)	tx.rollback();
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		session.close();
    	}
    }


	@Override
	public int totalPersons(int id, String name, String country)
			throws DAOException {
		try{
	        session = this.sessionFactory.openSession();
	        Criteria criteria = session.createCriteria(Person.class);
	        if(id != 0){
	        	criteria.add(Restrictions.eq("id",id));
	        }
	        if(name != null){
	        	criteria.add(Restrictions.eq("name",name));
	        }
	        if(country != null){
	        	criteria.add(Restrictions.eq("country",country));
	        }
	        	        
	        int total = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	        logger.info("Total person search by type:"+ total);
	        return total;
    	}catch(Exception ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
    	}
	}
	
}
