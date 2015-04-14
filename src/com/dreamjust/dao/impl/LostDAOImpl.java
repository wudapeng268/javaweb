package com.dreamjust.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dreamjust.dao.LostDAO;
import com.dreamjust.model.Address;
import com.dreamjust.model.Area;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.LostThing;
import com.dreamjust.util.HibernateUtil;

public class LostDAOImpl implements LostDAO {

	private static SessionFactory sf = HibernateUtil
			.getSessionFactory();
	private static AddressDAOImpl addrDAO = new AddressDAOImpl();
	private static PhotoDAOImpl photoDAOImpl = new PhotoDAOImpl();

	@Override
	public List<LostThing> getLostThingsByAddressAndColor(
			Address address, String color, String name) {
		Session session = sf.getCurrentSession();
		//"%' and lost.color like '%"
		//+ color.trim()
//		and lost.address.detailAddr like '%"
//		+ address.getDetailAddr()
//				.trim()
//		+  "%'
		//+ //ÔÝÊ±ºöÂÔ
		try {
			session.beginTransaction();
			Query q = session
					.createQuery("from LostThing lost where lost.otherThingid=0 and lost.address.cityid=:city  and lost.name like '%"
							+ name + "%'");
			q.setParameter("city" ,address.getCityid());
			List<LostThing> lostThings = q.list();
			session.getTransaction().commit();
			fillAddress(lostThings);
			fillAllphotos(lostThings);
			return lostThings;
		} catch (Exception e) {
			// TODO: handle exception
			session.getTransaction().rollback();
			return null;
		} finally {
		}

	}

	@Override
	public LostThing getLostThingById(int id) {
		Session session = sf.getCurrentSession();

		session.beginTransaction();
		LostThing lostThing = (LostThing) session.get(
				LostThing.class, id);
		session.getTransaction().commit();
		fillAddress(lostThing);
		fillAllphotos(lostThing);
		return lostThing;
	}

	private void fillAddress(List<LostThing> lostThings) {
		for (int i = 0; i < lostThings.size(); i++) {
			LostThing lostThing = lostThings.get(i);
			fillAddress(lostThing);
		}
	}

	private void fillAddress(LostThing lostThing) {
		Address address = lostThing.getAddress();
		address.setAreaName(addrDAO.getAreaById(
				address.getAreaid()).getAreaName());
		address.setCityName(addrDAO.getCityById(
				address.getCityid()).getCityName());
		address.setProvinceName(addrDAO.getProvinceById(
				address.getProvinceid()).getProvinceName());
	}

	@Override
	public List<LostThing> getLostThingByUserId(int userid) {
		Session session = sf.getCurrentSession();

		session.beginTransaction();
		Query q = session
				.createQuery("from LostThing lost where lost.userid="
						+ userid
						+ " order by lost.datetime desc");
		List<LostThing> lostThings = q.list();
		session.getTransaction().commit();
		fillAddress(lostThings);
		fillAllphotos(lostThings);
		return lostThings;
	}

	@Override
	public boolean updateLostThing(LostThing lost) {
		Session session = sf.getCurrentSession();
		try {

			session.beginTransaction();
			session.update(lost);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}

	@Override
	public int insertLostThing(LostThing lost) {
		Session session = sf.getCurrentSession();
		Transaction tr = session.beginTransaction();
		try {
			// session.beginTransaction();
			int save = (Integer) session.save(lost);
			tr.commit();
			return save;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tr.rollback();
			return -1;
		}
	}

	@Override
	public List<LostThing> getLostThingUnmate(int num) {
		Session session = sf.getCurrentSession();
		// sae¸ÄµÄ
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from LostThing lost where lost.otherThingid=0 order by lost.datetime desc,lost.id desc");
		query.setMaxResults(num);
		@SuppressWarnings("unchecked")
		List<LostThing> list = query.list();
		tx.commit();
		fillAddress(list);
		fillAllphotos(list);
		return list;
	}

	private void fillAllphotos(List<LostThing> list) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {

			list.set(i, fillAllphotos(list.get(i)));
		}
	}

	private LostThing fillAllphotos(LostThing lostThing) {
		// TODO Auto-generated method stub

		lostThing.setAllphotos(photoDAOImpl
				.getPhotoByLostThing(lostThing.getId()));
		return lostThing;
	}

	@Override
	public List<LostThing> getLostThingUnmate(int num,
			int startid) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from LostThing lost where lost.otherThingid=0 and lost.id<"
						+ startid
						+ " order by lost.datetime desc,lost.id desc");
		query.setMaxResults(num);
		List<LostThing> list = query.list();
		session.getTransaction().commit();
		fillAddress(list);
		fillAllphotos(list);

		return list;
	}

}
