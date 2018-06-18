package com.favorites.favoriteswebdemo.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.favorites.favoriteswebdemo.dao.UserRepository;
import com.favorites.favoriteswebdemo.domain.Favorites;
import com.favorites.favoriteswebdemo.domain.User;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserRepository userRepository;
	@Resource
	private FavoritesService favoritesService;
	@Resource
	private ConfigService configService;

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
		userRepository.save(user);
		// 添加默认收藏夹
		Favorites favorites = favoritesService.saveFavorites(user.getId(), "未读列表");
		// 添加默认属性设置
		configService.saveConfig(user.getId(),String.valueOf(favorites.getId()));
		
		return user;
	}

	@Override
	public User findByUserNameOrEmail(String userName, String email) {
		return userRepository.findByUserNameOrEmail(userName, email);
	}

	@Override
	public int setOutDateAndValidataCode(String outDate, String validataCode, String email) {
		return userRepository.setOutDateAndValidataCode(outDate, validataCode, email);
	}

	@Override
	public void setNewPassword(String passWord, String email) {
		userRepository.setNewPassword(passWord, email);
	}

}
