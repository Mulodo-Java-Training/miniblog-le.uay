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

import com.mulodo.miniblog.exeption.HandlerException;

/**
 * The generic of comment service
 * 
 * @author UayLU
 */
public interface GenericService<T>
{

    /**
     * add use to call add function in genericdao
     *
     * @param entity
     *            : match with entity in model package
     * @return Boolean
     * @exception HandlerException
     */
    public Boolean add(T entity) throws HandlerException;

    /**
     * update use to call update function in genericdao
     *
     * @param entity
     *            : match with entity in model package
     * @return Boolean
     * @exception HandlerException
     */
    public Boolean update(T entity) throws HandlerException;

    /**
     * delete use to call delete function in genericdao
     *
     * @param id
     *            : id of entity exist in database
     * @return Boolean
     * @exception HandlerException
     */
    public Boolean delete(int id) throws HandlerException;

    /**
     * findOne use to call findOne function in genericdao
     *
     * @param id
     *            : id of entity exist in database
     * @return T
     * @exception HandlerException
     */
    public T findOne(int id) throws HandlerException;

}
