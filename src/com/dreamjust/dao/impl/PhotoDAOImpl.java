package com.dreamjust.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dreamjust.dao.PhotoDAO;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.Photo;
import com.dreamjust.util.HibernateUtil;

public class PhotoDAOImpl implements PhotoDAO {
	SessionFactory sf = HibernateUtil.getSessionFactory();

	@Override
	public int insertPhoto(Photo photo) {
		Session session=sf.getCurrentSession();
		Transaction tr = session.beginTransaction();

		try 
		{
			int save = (Integer) session.save(photo);
			tr.commit();
			return save;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return -1;
		}
	}

	@Override
	public String[] getPhotoByLostThing(int lostThing) {
		// TODO Auto-generated method stub
		Session session=sf.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Photo photo where photo.lostThingid="+lostThing);
		List<Photo> list = query.list();
		session.getTransaction().commit();
		String[] urls=new String[list.size()];
		for (int i = 0; i < urls.length; i++) {
			urls[i]=list.get(i).getPhotoUrl();
		}
		return urls;
	}

	@Override
	public String[] getPhotoByFoundThing(int foundThing) {
		// TODO Auto-generated method stub
		Session session=sf.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Photo photo where photo.foundThingid="+foundThing);
		List<Photo> list = query.list();
		session.getTransaction().commit();
		String[] urls=new String[list.size()];
		for (int i = 0; i < urls.length; i++) {
			urls[i]=list.get(i).getPhotoUrl();
		}
		return urls;	
		}

}
