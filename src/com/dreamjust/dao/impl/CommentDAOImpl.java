package com.dreamjust.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dreamjust.dao.CommentDAO;
import com.dreamjust.model.Comment;
import com.dreamjust.model.User;
import com.dreamjust.util.HibernateUtil;

public class CommentDAOImpl implements CommentDAO {

	private static SessionFactory sf=HibernateUtil.getSessionFactory();
	
	public int insertComment(Comment comment) {
		// TODO Auto-generated method stub
		Session session=sf.getCurrentSession();
		session.beginTransaction();
		int savenum = (Integer) session.save(comment);
		session.getTransaction().commit();
		return savenum;
	}

	public List<Comment> getComments(int lostThingid, int foundThingid) {
		Session session=sf.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Comment comm where comm.lostThingid=:lostthing and comm.foundThingid=:foundthing");
		query.setParameter("lostthing", lostThingid);
		query.setParameter("foundthing", foundThingid);
		List<Comment> comments=query.list();
		session.getTransaction().commit();
		UserDAOImpl userDAOImpl=new UserDAOImpl();
		if (comments!=null) {
			for (int i = 0; i < comments.size(); i++) {
				User user=null;
				if (comments.get(i).getFoundUserid()==0) {
					user=userDAOImpl.getUserById(comments.get(i).getLostUserid());
					comments.get(i).setLostUsername(user.getUsername());
					comments.get(i).setLostUserImg(user.getImgUrl());
				}
				else {
					 user=userDAOImpl.getUserById(comments.get(i).getFoundUserid());
					comments.get(i).setFoundUsername(user.getUsername());
					comments.get(i).setFoundUserImg(user.getImgUrl());
				}
			}
		}
		
		return comments;
	}
	
	public List<Comment> getCommentsByUser(int userid) {
		Session session=sf.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Comment comm where comm.lostUserid=:lostid or comm.foundUserid=:foundid");
		query.setParameter("lostid", userid);
		query.setParameter("foundid", userid);
		List<Comment> comments=query.list();
		session.getTransaction().commit();
		UserDAOImpl userDAOImpl=new UserDAOImpl();
		for (int i = 0; i < comments.size(); i++) {
			User user=null;
			if (comments.get(i).getFoundUserid()==0) {
				user=userDAOImpl.getUserById(comments.get(i).getLostUserid());
				comments.get(i).setLostUsername(user.getUsername());
				comments.get(i).setLostUserImg(user.getImgUrl());
			}
			else {
				 user=userDAOImpl.getUserById(comments.get(i).getFoundUserid());
				comments.get(i).setFoundUsername(user.getUsername());
				comments.get(i).setFoundUserImg(user.getImgUrl());
			}
		}
		return comments;
	}   
}
