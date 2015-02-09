/* 
 * GenericService.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.exeption.ServiceException;

/**
 * The generic of comment service
 * 
 * @author UayLU
 * 
 */
public interface GenericService<T> {

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
	 public Boolean add(T entity) throws ServiceException;

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
     public Boolean update(T entity) throws ServiceException;

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
     public Boolean delete(int id) throws ServiceException;

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
     public T findOne(int id) throws ServiceException;

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
     public List<T> findAll(String tableName) throws ServiceException;
	
}
