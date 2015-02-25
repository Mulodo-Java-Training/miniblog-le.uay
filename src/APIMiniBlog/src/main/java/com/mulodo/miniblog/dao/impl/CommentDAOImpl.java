/* 
 * commentDAO.java 
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
import com.mulodo.miniblog.dao.CommentDAO;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.Comment;
import com.mulodo.miniblog.model.User;

/**
 * The comment dao impl
 * 
 * @author UayLU
 */
@Repository("commentDAO")
public class CommentDAOImpl extends GenericDAOImpl<Comment> implements CommentDAO
{

    private static final Logger logger = LoggerFactory.getLogger(CommentDAOImpl.class);

    /**
     * get_all_comment_for_post use to get all comment for post.
     *
     * @param post_id
     *            : id of post for search
     * @param isOwnerPost
     *            : result of check current user owner or not
     * @return Boolean
     * @exception HandlerException
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Comment> getAllCommentForPost(int post_id, Boolean isOwnerPost)
            throws HandlerException
    {
        try {
            session = this.sessionFactory.getCurrentSession();

            Criteria criteria = session.createCriteria(Comment.class, "comment");
            
            // create inner join with user table
            criteria.createAlias("user", "us");
            
            //get specific column 
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("id").as("id"))
                    .add(Projections.property("content").as("content"))
                    .add(Projections.property("created_at").as("created_at"))
                    .add(Projections.property("modified_at").as("modified_at"))
                    .add(Projections.property("us.id")).add(Projections.property("us.username"))
                    .add(Projections.property("us.status")));

            // create inner join with post table
            criteria.createAlias("post", "pt");
            // set post_id condition
            criteria.add(Restrictions.eq("pt.id", post_id));
            // if user is not owner post, get only post have active status 
            if (!isOwnerPost) {
                criteria.add(Restrictions.eq("pt.status", Constraints.POST_ACTIVE));
            }

            // set condition: order by created at
            criteria.addOrder(Order.desc("created_at"));

            // declare list comments
            List<Comment> listComments = new ArrayList<Comment>();
            if (!criteria.list().isEmpty()) {
                
                // get list data to list object
                List<Object[]> result = criteria.list();
                Comment comment = null;
                // get every array object in result, and transfer to comment
                for (Iterator<Object[]> it = result.iterator(); it.hasNext();) {
                    Object[] myResult = (Object[]) it.next();
                    comment = new Comment();
                    comment.setId((int) myResult[0]);
                    comment.setContent((String) myResult[1]);
                    comment.setCreated_at((Date) myResult[2]);
                    comment.setModified_at((Date) myResult[3]);
                    comment.setUser(new User((int) myResult[4], (String) myResult[5],
                            (int) myResult[6]));
                    // add comment to list object
                    listComments.add(comment);
                }

                for (Comment cm : listComments) {
                    logger.info("Post in get all post :" + cm.getId());
                }
            }
            return listComments;
        } catch (HibernateException ex) {

            throw new HandlerException(ex.getMessage());
        }
    }

    /**
     * get_all_comment_for_user use to get all comment for user
     *
     * @param post_id
     *            : id of post for search
     * @param isOwnerUser
     *            : result of check current user owner or not
     * @return Boolean
     * @exception HandlerException
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Comment> getAllCommentForUser(int user_id, Boolean isOwnerUser)
            throws HandlerException
    {

        try {
            session = this.sessionFactory.getCurrentSession();

            Criteria criteria = session.createCriteria(Comment.class, "comment");
            
            // create inner join with user table
            criteria.createAlias("user", "us");
            criteria.setProjection(Projections.projectionList().add(Projections.property("id"))
                    .add(Projections.property("content")).add(Projections.property("created_at"))
                    .add(Projections.property("modified_at")).add(Projections.property("us.id"))
                    .add(Projections.property("us.username"))
                    .add(Projections.property("us.status")));

            // set condition user_id
            criteria.add(Restrictions.eq("us.id", user_id));
            // create inner join with post table
            criteria.createAlias("post", "pt");
            // create inner join with user table from post table
            criteria.createAlias("post.user", "pu");
            
            if (isOwnerUser) {
                //if get all comment for currnet login user, set user_id to condition
                // between post and user table
                Criterion criterionPostUserActive = Restrictions.eq("pu.id", user_id);
                // set condition status active for other post that user is not owner
                Criterion criterionCurrentActive = Restrictions.eq("pt.status",
                        Constraints.POST_ACTIVE);
                // set condition get all comment of current login user and comment of post active
                criteria.add(Restrictions.or(criterionPostUserActive, criterionCurrentActive));
            } else {
                // if not current login user, get all commet from post active
                criteria.add(Restrictions.eq("pt.status", Constraints.POST_ACTIVE));
            }

            // set order comment by created time 
            criteria.addOrder(Order.desc("created_at"));

            List<Comment> listComments = new ArrayList<Comment>();
            if (!criteria.list().isEmpty()) {
                
                // get list data to list object (result)
                List<Object[]> result = criteria.list();
                Comment comment = null;
                for (Iterator<Object[]> it = result.iterator(); it.hasNext();) {
                    
                    // get every array object in result, and transfer to comment
                    Object[] myResult = (Object[]) it.next();
                    comment = new Comment();
                    comment.setId((int) myResult[0]);
                    comment.setContent((String) myResult[1]);
                    comment.setCreated_at((Date) myResult[2]);
                    comment.setModified_at((Date) myResult[3]);
                    comment.setUser(new User((int) myResult[4], (String) myResult[5],
                            (int) myResult[6]));
                    
                    // add comment to list comments
                    listComments.add(comment);
                    logger.info("Comment in get all commente for user :" + comment.getId());
                }
            }
            return listComments;
        } catch (HibernateException ex) {

            throw new HandlerException(ex.getMessage());
        }
    }

    /**
     * findByContent use for find comment by content in the database for unit
     * test
     *
     * @param content
     *            : content of comment
     * @return Comment
     * @exception HandlerException
     */
    @Override
    @Transactional
    public Comment findByContent(String content) throws HandlerException
    {
        Comment comment = null;

        try {
            session = this.sessionFactory.getCurrentSession();

            Criteria criteria = session.createCriteria(Comment.class);
            criteria.add(Restrictions.eq("content", content));
            if (!criteria.list().isEmpty()) {
                comment = (Comment) criteria.list().get(0);
                logger.info("Find user successfully, user details=" + comment);
            }
            return comment;
        } catch (HibernateException ex) {

            logger.info("Hibernate exception, Details=" + ex.getMessage());

            throw new HandlerException(ex.getMessage());
        }
    }

}
