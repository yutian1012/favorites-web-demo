package com.favorites.favoriteswebdemo.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.favorites.favoriteswebdemo.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByEmail(String email);
	
	public User findByUserName(String userName);
	
	public User findByUserNameOrEmail(String username, String email);
	
	@Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User set outDate=:outDate, validataCode=:validataCode where email=:email") 
    int setOutDateAndValidataCode(@Param("outDate") String outDate, @Param("validataCode") String validataCode, @Param("email") String email);
	
	@Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User set passWord=:passWord where email=:email") 
    int setNewPassword(@Param("passWord") String passWord, @Param("email") String email);
}
