package com.dreamjust.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dreamjust.dao.FoundDAO;
import com.dreamjust.model.Address;
import com.dreamjust.model.Area;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.LostThing;
import com.dreamjust.util.HibernateUtil;

public class FoundDAOImpl implements FoundDAO {

	private static SessionFactory sf = HibernateUtil
			.getSessionFactory();
	private static AddressDAOImpl addrDAO = new AddressDAOImpl();
	private static PhotoDAOImpl photoDAOImpl = new PhotoDAOImpl();

	@Override
	public List<FoundThing> getFoundThingsByAddressAndColor(
			Address address, String color, String name) {
		Session session = sf.getCurrentSession();
//+ "%' and found.color like '%"
////		+ color.trim()
//		found.address.detailAddr like '%"
//		+ address.getDetailAddr()
//				.trim()+
//		 "%' and
//		+//暂时忽略颜色的比较
		try {
			session.beginTransaction();
			Query q = session
					.createQuery("from FoundThing found where found.otherThingid=0 and found.address.cityid=:city and  found.name like '%"
							+ name.trim() + "%'");
			q.setParameter("city", address.getCityid());
			List<FoundThing> foundThings = q.list();
			session.getTransaction().commit();

			fillAddress(foundThings);
			fillAllphotos(foundThings);

			System.out
					.println("------------------------------");
			System.out.println(foundThings);
			return foundThings;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		} finally {
		}

	}

	private void fillAllphotos(List<FoundThing> foundThings) {
		// TODO Auto-generated method stub
		for (int i = 0; i < foundThings.size(); i++) {

			foundThings.set(i,
					fillAllphotos(foundThings.get(i)));
		}
	}

	private FoundThing fillAllphotos(FoundThing foundThing) {
		// TODO Auto-generated method stub
		foundThing.setAllphotos(photoDAOImpl
				.getPhotoByFoundThing(foundThing.getId()));
		return foundThing;
	}

	private static void fillAddress(
			List<FoundThing> foundThings) {
		for (int i = 0; i < foundThings.size(); i++) {
			FoundThing foundThing = foundThings.get(i);
			fillAddress(foundThing);
		}
	}

	private static void fillAddress(FoundThing foundThing) {
		Address address = foundThing.getAddress();
		address.setAreaName(addrDAO.getAreaById(
				address.getAreaid()).getAreaName());
		address.setCityName(addrDAO.getCityById(
				address.getCityid()).getCityName());
		address.setProvinceName(addrDAO.getProvinceById(
				address.getProvinceid()).getProvinceName());
	}

	@Override
	public FoundThing getFoundThingById(int id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		FoundThing foundThing = (FoundThing) session.get(
				FoundThing.class, id);
		session.getTransaction().commit();
		fillAddress(foundThing);
		fillAllphotos(foundThing);
		return foundThing;
	}

	@Override
	public List<FoundThing> getFoundThingByUserId(int userid) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query q = session
				.createQuery("from FoundThing found where found.userid="
						+ userid
						+ " order by found.datetime desc");
		List<FoundThing> foundThings = q.list();
		session.getTransaction().commit();
		fillAddress(foundThings);
		fillAllphotos(foundThings);

		return foundThings;
	}

	@Override
	public boolean updateFoundThing(FoundThing found) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		try {

			session.beginTransaction();
			session.update(found);
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
	public int insertFoundThing(FoundThing found) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		try {
			session.beginTransaction();
			int save = (Integer) session.save(found);
			session.getTransaction().commit();
			return save;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return -1;
		}
	}

	@Override
	public List<FoundThing> getFoundThingUnmate(int num) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from FoundThing found where found.otherThingid=0 order by found.datetime desc,found.id desc");
		query.setMaxResults(num);
		List<FoundThing> list = query.list();
		session.getTransaction().commit();
		fillAddress(list);
		fillAllphotos(list);

		return list;
	}

	@Override
	public List<FoundThing> getFoundThingUnmate(int num,
			int startid) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from FoundThing found where found.otherThingid=0 and found.id<"
						+ startid
						+ " order by found.datetime desc,found.id desc");
		query.setMaxResults(num);
		List<FoundThing> list = query.list();
		session.getTransaction().commit();
		fillAddress(list);
		fillAllphotos(list);

		return list;
	}

}
