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

import com.mulodo.miniblog.exeption.HandlerException;

/**
 * The interface of generic dao
 * 
 * @author UayLU
 */
public interface GenericDAO<T>
{

    /**
     * add use to add new entity to database
     *
     * @param entity
     *            : match with entity in model package
     * @return Boolean
     * @exception HandlerException
     */
    public Boolean add(T entity) throws HandlerException;

    /**
     * update use to update new information of entity to database
     *
     * @param entity
     *            : match with entity in model package
     * @return Boolean
     * @exception HandlerException
     */
    public Boolean update(T entity) throws HandlerException;

    /**
     * delete use to delete entity exist in database
     *
     * @param id
     *            : id of entity in database
     * @return Boolean
     * @exception HandlerException
     */
    public Boolean delete(int id) throws HandlerException;

    /**
     * findOne use to find one entity exist in database
     *
     * @param id
     *            : id of entity in database
     * @return T
     * @exception HandlerException
     */
    public T findOne(int id) throws HandlerException;

}