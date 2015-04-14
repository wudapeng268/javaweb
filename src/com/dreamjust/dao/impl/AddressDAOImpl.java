package com.dreamjust.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dreamjust.model.Area;
import com.dreamjust.model.City;
import com.dreamjust.model.Province;
import com.dreamjust.util.HibernateUtil;

public class AddressDAOImpl {
	private static SessionFactory sf=HibernateUtil.getSessionFactory();

	public List<Province> findProvinces()
	{
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("from Province");
		List<Province> province = q.list();
		session.getTransaction().commit();
		return province;
	}
	
	public List<City> getCityByFather(int fatherid)
	{
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("from City c where c.cityFather="+fatherid);
		List<City> citys = q.list();
		session.getTransaction().commit();
		return citys;
	}

	public List<Area> getAreaByFather(int cityid) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("from Area a where a.areaFather="+cityid);
		List<Area> areas = q.list();
		session.getTransaction().commit();
		return areas;
	}

	public Province getProvinceById(int id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Province p = (Province) session.get(Province.class, id);
		session.getTransaction().commit();
		return p;
	}
	
	public City getCityById(int id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		City c = (City) session.get(City.class, id);
		session.getTransaction().commit();
		return c;
	}
	
	public Area getAreaById(int id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Area a = (Area) session.get(Area.class, id);
		session.getTransaction().commit();
		return a;
	}
}
