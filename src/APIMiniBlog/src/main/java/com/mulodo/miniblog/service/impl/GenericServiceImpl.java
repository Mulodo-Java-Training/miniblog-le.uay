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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.GenericDAO;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.service.GenericService;

/**
 * The generic service impl
 * 
 * @author UayLU
 */
@Service
public abstract class GenericServiceImpl<T> implements GenericService<T>
{

    protected GenericDAO<T> genericDAO;

    /**
     * setGenericDAO use to set datasource from applicationcontent.xml
     *
     * @param genericDAO
     *            : genericDAO from datasource
     * @return void
     */
    public void setGenericDAO(GenericDAO<T> genericDAO)
    {
        this.genericDAO = genericDAO;
    }

    /**
     * add use to call add function in genericdao
     *
     * @param entity
     *            : match with entity in model package
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    public Boolean add(T entity) throws HandlerException
    {
        return this.genericDAO.add(entity);
    }

    /**
     * update use to call update function in genericdao
     *
     * @param entity
     *            : match with entity in model package
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    public Boolean update(T entity) throws HandlerException
    {
        return this.genericDAO.update(entity);
    }

    /**
     * delete use to call delete function in genericdao
     *
     * @param id
     *            : id of entity exist in database
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    public Boolean delete(int id) throws HandlerException
    {
        return this.genericDAO.delete(id);
    }

    /**
     * findOne use to call findOne function in genericdao
     *
     * @param id
     *            : id of entity exist in database
     * @return T
     * @exception HandlerException
     */
    @Override
    @Transactional
    public T findOne(int id) throws HandlerException
    {
        return this.genericDAO.findOne(id);
    }

}
