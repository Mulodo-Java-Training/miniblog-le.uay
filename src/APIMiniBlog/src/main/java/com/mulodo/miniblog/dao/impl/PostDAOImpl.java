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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.dao.PostDAO;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.model.User;

/**
 * The post dao impl
 * 
 * @author UayLU
 */
@Repository("postDAO")
public class PostDAOImpl extends GenericDAOImpl<Post> implements PostDAO
{

    private static final Logger logger = LoggerFactory.getLogger(PostDAOImpl.class);

    /**
     * get_all_post use to get all post from database
     *
     * @param author_id
     *            : user id that owner post
     * @param isForUser
     *            : result of check current user owner or not
     * @param pageNume
     *            : the number of page you want to get
     * @return List<Post>
     * @exception HandlerException
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Post> getAllPost(int pageNum, int author_id, String description, Boolean isForUser,
            Boolean isOwnerUser) throws HandlerException
    {
        List<Post> listPost = null;
        try {
            session = this.sessionFactory.getCurrentSession();

            Criteria criteria = session.createCriteria(Post.class, "post");

            if (pageNum > 0) {
                criteria.setFirstResult((pageNum - 1) * Constraints.LIMIT_ROW);
                criteria.setMaxResults(Constraints.LIMIT_ROW);
            }

            // create inner join user table
            criteria.createAlias("user", "us");
            // get specific column
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("id").as("id"))
                    .add(Projections.property("title").as("title"))
                    .add(Projections.property("content").as("content"))
                    .add(Projections.property("created_at").as("created_at"))
                    .add(Projections.property("modified_at").as("modified_at"))
                    .add(Projections.property("status").as("status"))
                    .add(Projections.property("us.id").as("author_id"))
                    .add(Projections.property("us.username").as("username"))
                    .add(Projections.property("us.firstname").as("firstname"))
                    .add(Projections.property("us.lastname").as("lastname"))
                    .add(Projections.property("us.status").as("status")));

            // if author id positive integer and get post for user
            if (author_id > 0 && isForUser) {
                // get all post for user
                if (isOwnerUser) {
                    // get all post for current user that user owner
                    criteria.add(Restrictions.eq("us.id", author_id));
                } else {
                    // get all post for other user with condition:
                    // user active and post of that user is active
                    criteria.add(Restrictions.eq("us.id", author_id));
                    criteria.add(Restrictions.eq("us.status", Constraints.USER_ACTIVE));
                    criteria.add(Restrictions.eq("status", Constraints.POST_ACTIVE));
                }
            } else if (author_id > 0 && !isForUser) {
                // if get post not for user, follow condition:
                // 1: get post of other user :post have active status and user is active user  
                // 2: get post of current login user: all post of current login user
                Criterion criterionPostUserActive = Restrictions.and(
                        Restrictions.eq("status", Constraints.POST_ACTIVE),
                        Restrictions.eq("us.status", Constraints.USER_ACTIVE));
                Criterion criterionCurrentActive = Restrictions.eq("us.id", author_id);
                criteria.add(Restrictions.or(criterionCurrentActive, criterionPostUserActive));
            }
            // set condition title and content like description
            if (description != null) {
                criteria.add(Restrictions.disjunction()
                        .add(Restrictions.like("title", "%" + description + "%"))
                        .add(Restrictions.like("content", "%" + description + "%")));
            }
            
            // add order post by created time
            criteria.addOrder(Order.desc("created_at"));

            if (!criteria.list().isEmpty()) {
                listPost = new ArrayList<Post>();
                // get list post from data to list object(result)
                List<Object[]> result = criteria.list();
                
                Post post = null;

                for (Iterator<Object[]> it = result.iterator(); it.hasNext();) {
                    
                    // get every array object in result, and transfer to comment
                    Object[] myResult = (Object[]) it.next();
                    post = new Post();
                    post.setId((int) myResult[0]);
                    post.setTitle((String) myResult[1]);
                    post.setContent((String) myResult[2]);
                    post.setCreated_at((Date) myResult[3]);
                    post.setModified_at((Date) myResult[4]);
                    post.setStatus((int) myResult[5]);
                    post.setUser(new User((int) myResult[6], (String) myResult[7], 
                            (String)  myResult[8], (String)  myResult[9], (int) myResult[10]));
                    
                    //add post to list post
                    listPost.add(post);
                    logger.info("Comment in get all commente for user :" + post.getId());
                }
                return listPost;
            } else {
                return null;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new HandlerException(ex.getMessage());
        }

    }

    /**
     * get_all_post_size use to get size of post with owner user from database
     *
     * @param author_id
     *            : user id that owner post
     * @param isForUser
     *            : result of get post for user
     * @param isOwnerUser
     *            : result of check current user owner or not
     * @return int
     * @exception HandlerException
     */
    @Override
    @Transactional
    public int getAllPostSize(int author_id, String description, Boolean isForUser,
            Boolean isOwnerUser) throws HandlerException
    {
        try {
            session = this.sessionFactory.getCurrentSession();

            Criteria criteria = session.createCriteria(Post.class, "post");

            criteria.createAlias("user", "us");

            // if author id positive integer and get post for user
            if (author_id > 0 && isForUser) {
                
                //get all post for user
                if (isOwnerUser) {
                    criteria.add(Restrictions.eq("us.id", author_id));
                } else {
                    // get all post for other user with condition:
                    // user active and post of that user is active
                    criteria.add(Restrictions.eq("us.id", author_id));
                    criteria.add(Restrictions.eq("us.status", Constraints.USER_ACTIVE));
                    criteria.add(Restrictions.eq("status", Constraints.POST_ACTIVE));
                }
            } else if (author_id > 0 && !isForUser) {
                // if get post not for user, follow condition:
                // 1: get post of other user :post have active status and user is active user  
                // 2: get post of current login user: all post of current login user
                Criterion criterionPostUserActive = Restrictions.and(
                        Restrictions.eq("status", Constraints.POST_ACTIVE),
                        Restrictions.eq("us.status", Constraints.USER_ACTIVE));
                Criterion criterionCurrentActive = Restrictions.eq("us.id", author_id);
                criteria.add(Restrictions.or(criterionCurrentActive, criterionPostUserActive));
            }

            // set condition title and content like description
            if (description != null) {
                criteria.add(Restrictions.disjunction()
                        .add(Restrictions.like("title", "%" + description + "%"))
                        .add(Restrictions.like("content", "%" + description + "%")));
            }

            Integer totalPost = ((Number) criteria.setProjection(Projections.rowCount())
                    .uniqueResult()).intValue();
            return totalPost;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new HandlerException(ex.getMessage());
        }
    }

    /**
     * deleteByTitle use for delete post by title in the database
     *
     * @param title
     *            : title of post
     * @return void
     * @exception HandlerException
     */
    @Override
    @Transactional
    public void deleteByTitle(String title) throws HandlerException
    {
        try {
            session = this.sessionFactory.getCurrentSession();

            Criteria criteria = session.createCriteria(Post.class);

            criteria.add(Restrictions.eq("title", title));
            if (!criteria.list().isEmpty()) {
                Post post = (Post) criteria.list().get(0);
                session.delete(post);
                logger.info("Post deleted successfully, post details=" + post);
            }

        } catch (HibernateException ex) {
            ex.printStackTrace();
            logger.info("Hibernate exception, Details=" + ex.getMessage());
            throw new HandlerException(ex.getMessage());
        }

    }

    /**
     * findByTitle use for find post by title in the database for unit test
     *
     * @param title
     *            : title of post
     * @return Post
     * @exception HandlerException
     */
    @Override
    @Transactional
    public Post findByTitle(String title) throws HandlerException
    {
        try {
            session = this.sessionFactory.getCurrentSession();

            Criteria criteria = session.createCriteria(Post.class);

            criteria.add(Restrictions.eq("title", title));
            Post post = null;
            if (criteria.list().isEmpty()) {
                return null;
            } else {
                post = (Post) criteria.list().get(0);
            }

            logger.info("Post find by title successfully, post details=" + post);
            return post;

        } catch (HibernateException ex) {
            logger.info("Hibernate exception, Details=" + ex.getMessage());
            ex.printStackTrace();
            throw new HandlerException(ex.getMessage());
        }
    }
}