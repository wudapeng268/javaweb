package com.dreamjust.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dreamjust.dao.UserDAO;
import com.dreamjust.model.User;
import com.dreamjust.util.HibernateUtil;
import com.dreamjust.util.StaticVariable;

public class UserDAOImpl implements UserDAO {

	private static SessionFactory sf = HibernateUtil
			.getSessionFactory();

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery(
				"from User u").list();
		session.getTransaction().commit();
		return users;
	}

	@Override
	public User getUserById(int id) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, id);
		session.getTransaction().commit();
		return user;
	}

	@Override
	public boolean updateUser(User u) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		try {
			session.beginTransaction();
			session.update(u);
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
	public int insertUser(User u) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		try {
			session.beginTransaction();
			int save = (Integer) session.save(u);
			session.getTransaction().commit();
			return save;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public User getUserByEmail(String email) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session
					.createQuery("from User u where u.email='"
							+ email+"'");
			List<User> users = query.list();
			session.getTransaction().commit();
			if (users.size()!=0) {
				return users.get(0);
			}
			else {
				throw new Exception("没有这个用户名");
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//			session.getTransaction().rollback();
			return null;
		}
	}

	@Override
	public User getUserByUsername(String username) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session
					.createQuery("from User u where u.username='"
							+ username+"'");
			List<User> users = query.list();
			
			return users.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
		finally
		{
			session.getTransaction().commit();
		}
	}

	/**
	 * 荣誉榜使用,通过price排序
	 * 
	 * @param num
	 *            请求的条数
	 * @return
	 */
	public List<User> getUserByPrice(int num) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from User u order by u.price desc");
		try {

			List<User> users = query.list();
			session.getTransaction().commit();
			return users;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}

	}
	
	public static void UpdateUserSort()
	{
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from User u order by u.price desc");
		

		try {

			List<User> users = query.list();
			session.getTransaction().commit();
			StaticVariable.sorts.clear();
			for (int i = 0; i < users.size(); i++) {
				StaticVariable.sorts.put(users.get(i).getId(),i+1);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

}
