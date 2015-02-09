/* 
 * GenericServiceImpl.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.GenericDAO;
import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.exeption.ServiceException;
import com.mulodo.miniblog.service.GenericService;

/**
 * The generic service impl
 * 
 * @author UayLU
 * 
 */
@Service
public abstract class GenericServiceImpl<T> implements GenericService<T>{


    protected GenericDAO<T> genericDAO;
    
    /**
	 *  setGenericDAO use to set datasource from applicationcontent.xml
	 *	
	 *	@param	genericDAO : genericDAO from datasource
	 *
	 *	@return void
	 */
    public void setGenericDAO(GenericDAO<T> genericDAO) {
		this.genericDAO = genericDAO;
	}
    
    /**
	 *  add use to call add function in genericdao
	 *	
	 *	@param	entity : match with entity in model package
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	public Boolean add(T entity) throws ServiceException {
		try{
			return this.genericDAO.add(entity);
			 
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	/**
	 *  update use to call update function in genericdao 
	 *	
	 *	@param	entity : match with entity in model package
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	public Boolean update(T entity) throws ServiceException {
		try{
			return this.genericDAO.update(entity);
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	/**
	 *  delete use to call delete function in genericdao 
	 *	
	 *	@param	id : id of entity exist in database
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	public Boolean delete(int id) throws ServiceException {
		try{
			return this.genericDAO.delete(id);
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	/**
	 *  findOne use to call findOne function in genericdao 
	 *	
	 *	@param	id : id of entity exist in database
	 *
	 *	@return T
	 *	
	 *	
	 *  @exception  ServiceException
	 */   
	@Override
    @Transactional
	public T findOne(int id) throws ServiceException {
		try{
			return this.genericDAO.findOne(id);
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}catch(Exception ex){
			System.out.println("acd");
			throw new ServiceException(ex.getMessage());
		}
	}

	/**
 	 *  findOne use to call findOne function in genericdao 
 	 *	
 	 *	@param	table : table of entity match with entity in model package
 	 *
 	 *	@return List<T>
 	 *	
 	 *	
 	 *  @exception  ServiceException
 	 */ 
	@Override
	public List<T> findAll(String tableName) throws ServiceException {
		try{
			return this.genericDAO.findAll(tableName);
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

}
