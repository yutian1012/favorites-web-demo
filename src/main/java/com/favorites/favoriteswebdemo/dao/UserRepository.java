package com.favorites.favoriteswebdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favorites.favoriteswebdemo.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByEmail(String email);
	
	public User findByUserName(String userName);
	
	public User findByUserNameOrEmail(String username, String email);
}
