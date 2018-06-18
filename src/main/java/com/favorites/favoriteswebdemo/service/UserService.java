package com.favorites.favoriteswebdemo.service;

import com.favorites.favoriteswebdemo.domain.User;

public interface UserService {
	public User findByEmail(String email);
	
	public User findByUserName(String userName);
	
	public User save(User user);
	
	public User findByUserNameOrEmail(String userName,String email);
	
	public int setOutDateAndValidataCode(String outDate, String validataCode, String email);
	
	public void setNewPassword(String passWord, String email);
}
