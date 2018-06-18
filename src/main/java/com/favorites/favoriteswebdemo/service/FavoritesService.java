package com.favorites.favoriteswebdemo.service;

import com.favorites.favoriteswebdemo.domain.Favorites;

public interface FavoritesService {
	
	public Favorites findById(Long id);
	
	public Favorites saveFavorites(Long userId,String name);
}
