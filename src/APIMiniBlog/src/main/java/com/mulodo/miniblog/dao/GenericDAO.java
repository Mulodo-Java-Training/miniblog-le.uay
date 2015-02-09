/* 
 * GenericDAO.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.exeption.DAOException;

/**
 * The interface of generic dao
 * 
 * @author UayLU
 * 
 */
public interface GenericDAO<T> {

	/**
	 *  add use to add new entity to database
	 *	
	 *	@param	entity : match with entity in model package
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  DAOException
	 */
    public Boolean add(T entity) throws DAOException;

    /**
	 *  update use to update new information of entity to database
	 *	
	 *	@param	entity : match with entity in model package
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  DAOException
	 */
    public Boolean update(T entity) throws DAOException;

    /**
	 *  delete use to delete entity exist in database
	 *	
	 *	@param	id : id of entity in database
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  DAOException
	 */
    public Boolean delete(int id) throws DAOException;
    
    /**
	 *  findOne use to find one entity exist in database
	 *	
	 *	@param	id : id of entity in database
	 *
	 *	@return T
	 *	
	 *	
	 *  @exception  DAOException
	 */
    public T findOne(int id) throws DAOException;

    /**
	 *  findAll use to find all entity exist in database
	 *	
	 *	@param	tableName : table need for find
	 *
	 *	@return List<T>
	 *	
	 *	
	 *  @exception  DAOException
	 */
    public List<T> findAll(String tableName) throws DAOException;

}