package com.favorites.favoriteswebdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favorites.favoriteswebdemo.domain.Favorites;

public interface FavoritesRepository extends JpaRepository<Favorites, Long>{
	
	Favorites findById(long  id);

	List<Favorites> findByUserId(Long userId);

	List<Favorites> findByUserIdOrderByIdDesc(Long userId);

	Favorites findByUserIdAndName(Long userId,String name);

}
