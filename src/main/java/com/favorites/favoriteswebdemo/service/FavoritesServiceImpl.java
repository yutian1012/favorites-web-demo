package com.favorites.favoriteswebdemo.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.favorites.favoriteswebdemo.dao.FavoritesRepository;
import com.favorites.favoriteswebdemo.domain.Favorites;

@Service
@Transactional
public class FavoritesServiceImpl implements FavoritesService{
	
	@Resource
	private FavoritesRepository favoritesRepository;

	@Override
	public Favorites saveFavorites(Long userId, String name) {
		Favorites favorites = new Favorites();
		favorites.setName(name);
		favorites.setUserId(userId);
		favorites.setCount(0l);
		favorites.setPublicCount(10l);
		favorites.setCreateTime(System.currentTimeMillis());
		favorites.setLastModifyTime(System.currentTimeMillis());
		favoritesRepository.save(favorites);
		return  favorites;
	}

	@Override
	public Favorites findById(Long id) {
		return favoritesRepository.findById(id.longValue());
	}

}
