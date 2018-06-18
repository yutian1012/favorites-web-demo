package com.favorites.favoriteswebdemo.service;

import com.favorites.favoriteswebdemo.domain.Config;

public interface ConfigService {
	public Config saveConfig(Long userId,String favoritesId);

	public void updateConfig(long id ,String type,String defaultFavorites);
	
	public Config findByUserId(Long userId);
}
