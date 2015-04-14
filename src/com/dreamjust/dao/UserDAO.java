package com.dreamjust.dao;

import java.util.List;

import com.dreamjust.model.User;

public interface UserDAO {

	public List<User> getUsers();
	
	public User getUserById(int id);
	
	public User getUserByEmail(String email);
	
	public User getUserByUsername(String username);
	
	public boolean updateUser(User u);
	
	public int insertUser(User u);
	
	public List<User> getUserByPrice(int num);
	
	
	
}
