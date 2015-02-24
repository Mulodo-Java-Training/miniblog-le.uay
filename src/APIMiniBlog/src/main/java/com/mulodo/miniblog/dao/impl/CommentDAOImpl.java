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
    public List<Comment> getAllCommentForPost(int post_id, Boolean isOwnerPost)
            throws HandlerException
    {
        try {
            session = this.sessionFactory.openSession();

            Criteria criteria = session.createCriteria(Comment.class, "comment");

            criteria.createAlias("user", "us");
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("id").as("id"))
                    .add(Projections.property("content").as("content"))
                    .add(Projections.property("created_at").as("created_at"))
                    .add(Projections.property("modified_at").as("modified_at"))
                    .add(Projections.property("us.id")).add(Projections.property("us.username"))
                    .add(Projections.property("us.status")));

            criteria.createAlias("post", "pt");
            criteria.add(Restrictions.eq("pt.id", post_id));
            if (!isOwnerPost) {
                criteria.add(Restrictions.eq("pt.status", Constraints.POST_ACTIVE));
            }

            criteria.addOrder(Order.desc("created_at"));

            if (!criteria.list().isEmpty()) {
                List<Comment> listComments = new ArrayList<Comment>();
                List<Object[]> result = criteria.list();
                Comment comment = null;
                for (Iterator<Object[]> it = result.iterator(); it.hasNext();) {
                    Object[] myResult = (Object[]) it.next();
                    comment = new Comment();
                    comment.setId((int) myResult[0]);
                    comment.setContent((String) myResult[1]);
                    comment.setCreated_at((Date) myResult[2]);
                    comment.setModified_at((Date) myResult[3]);
                    comment.setUser(new User((int) myResult[4], (String) myResult[5],
                            (int) myResult[6]));
                    listComments.add(comment);
                }

                for (Comment cm : listComments) {
                    logger.info("Post in get all post :" + cm.getId());
                }
                return listComments;
            } else {
                return null;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new HandlerException(ex.getMessage());
        } finally {
            session.close();
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
    public List<Comment> getAllCommentForUser(int user_id, Boolean isOwnerUser)
            throws HandlerException
    {
        System.out.println("user id " + user_id);

        try {
            session = this.sessionFactory.openSession();

            Criteria criteria = session.createCriteria(Comment.class, "comment");

            criteria.createAlias("user", "us");
            criteria.setProjection(Projections.projectionList().add(Projections.property("id"))
                    .add(Projections.property("content")).add(Projections.property("created_at"))
                    .add(Projections.property("modified_at")).add(Projections.property("us.id"))
                    .add(Projections.property("us.username"))
                    .add(Projections.property("us.status")));
            // .setResultTransformer(new
            // AliasToBeanResultTransformer(Comment.class));

            criteria.add(Restrictions.eq("us.id", user_id));

            criteria.createAlias("post", "pt");
            criteria.createAlias("post.user", "pu");
            if (isOwnerUser) {
                Criterion criterionPostUserActive = Restrictions.eq("pu.id", user_id);
                Criterion criterionCurrentActive = Restrictions.eq("pt.status",
                        Constraints.POST_ACTIVE);
                criteria.add(Restrictions.or(criterionPostUserActive, criterionCurrentActive));
            } else {
                criteria.add(Restrictions.eq("pt.status", Constraints.POST_ACTIVE));
            }

            criteria.addOrder(Order.desc("created_at"));

            if (!criteria.list().isEmpty()) {
                List<Comment> listComments = new ArrayList<Comment>();
                List<Object[]> result = criteria.list();
                System.out.println("size " + criteria.list().size());
                Comment comment = null;
                for (Iterator<Object[]> it = result.iterator(); it.hasNext();) {
                    Object[] myResult = (Object[]) it.next();
                    comment = new Comment();
                    comment.setId((int) myResult[0]);
                    comment.setContent((String) myResult[1]);
                    comment.setCreated_at((Date) myResult[2]);
                    comment.setModified_at((Date) myResult[3]);
                    comment.setUser(new User((int) myResult[4], (String) myResult[5],
                            (int) myResult[6]));
                    listComments.add(comment);
                    logger.info("Comment in get all commente for user :" + comment.getId());
                }
                return listComments;
            } else {
                return null;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new HandlerException(ex.getMessage());
        } finally {
            session.close();
        }
    }

}
