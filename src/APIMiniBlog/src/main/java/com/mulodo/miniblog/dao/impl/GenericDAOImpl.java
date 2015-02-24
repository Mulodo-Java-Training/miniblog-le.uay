/* 
 * GenericDAOImpl.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;

import com.mulodo.miniblog.dao.GenericDAO;
import com.mulodo.miniblog.exeption.HandlerException;

/**
 * The abstract class of generic dao impl
 * 
 * @author UayLU
 */
public abstract class GenericDAOImpl<T> implements GenericDAO<T>
{

    protected SessionFactory sessionFactory;
    protected Session session = null;
    protected Transaction tx = null;
    private static final Logger logger = LoggerFactory.getLogger(GenericDAOImpl.class);
    private Class<T> genericType;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    /**
     * add use to add new entity to database
     *
     * @param entity
     *            : match with entity in model package
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    public Boolean add(T entity) throws HandlerException
    {
        try {
            session = this.sessionFactory.openSession();
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            logger.info("Entity saved successfully, Entity Details=" + entity);
            return true;
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            logger.info("Hibernate exception, Details=" + ex.getMessage());
            ex.printStackTrace();
            throw new HandlerException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * update use to update new information of entity to database
     *
     * @param entity
     *            : match with entity in model package
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    public Boolean update(T entity) throws HandlerException
    {
        try {
            session = this.sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            logger.info("Entity updated successfully, Entity Details=" + entity);
            return true;
        } catch (HibernateException ex) {
            logger.info("Hibernate exception, Details=" + ex.getMessage());
            tx.rollback();
            ex.printStackTrace();
            throw new HandlerException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * delete use to delete entity exist in database
     *
     * @param id
     *            : id of entity in database
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    @SuppressWarnings("unchecked")
    public Boolean delete(int id) throws HandlerException
    {
        try {

            this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(),
                    GenericDAOImpl.class);
            session = this.sessionFactory.openSession();
            tx = session.beginTransaction();
            T p = (T) session.load(this.genericType, id);
            if (p != null) {
                session.delete(p);
            }
            tx.commit();
            logger.info("Entity deleted successfully, Entity details=" + p);
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            logger.info("Hibernate exception, Details=" + ex.getMessage());
            ex.printStackTrace();
            throw new HandlerException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * findOne use to find one entity exist in database
     *
     * @param id
     *            : id of entity in database
     * @return T
     * @exception HandlerException
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findOne(int id) throws HandlerException
    {
        try {
            this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(),
                    GenericDAOImpl.class);
            session = this.sessionFactory.openSession();
            T p = (T) session.get(this.genericType, id);
            if (p != null) {
                logger.info("Entity loaded successfully, Entity details=" + p);
            }
            return p;
        } catch (HibernateException ex) {
            logger.info("Hibernate exception, Details=" + ex.getMessage());
            ex.printStackTrace();
            throw new HandlerException(ex.getMessage());
        } finally {
            session.close();
        }
    }

}
