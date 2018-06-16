package com.favorites.favoriteswebdemo.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.favorites.favoriteswebdemo.dao.UserRepository;
import com.favorites.favoriteswebdemo.domain.User;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserRepository userRepository;

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findByUserNameOrEmail(String userName, String email) {
		return userRepository.findByUserNameOrEmail(userName, email);
	}

}
