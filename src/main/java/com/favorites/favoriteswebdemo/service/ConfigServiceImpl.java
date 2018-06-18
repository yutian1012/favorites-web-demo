package com.favorites.favoriteswebdemo.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.favorites.favoriteswebdemo.dao.ConfigRepository;
import com.favorites.favoriteswebdemo.domain.Config;

@Service
@Transactional
public class ConfigServiceImpl implements ConfigService{
	
	@Resource
	private ConfigRepository configRepository;

	@Override
	public Config saveConfig(Long userId, String favoritesId) {
		Config config = new Config();
		config.setUserId(userId);
		config.setDefaultModel("simple");
		config.setDefaultFavorties(favoritesId);
		config.setDefaultCollectType("public");
		config.setCreateTime(System.currentTimeMillis());
		config.setLastModifyTime(System.currentTimeMillis());
		configRepository.save(config);
		return config;
	}

	@Override
	public void updateConfig(long id, String type, String defaultFavorites) {
		Config config = configRepository.findById(id);
		String value="";
		if("defaultCollectType".equals(type)){
			if("public".equals(config.getDefaultCollectType())){
				value = "private";
			}else{
				value = "public";
			}
			configRepository.updateCollectTypeById(id, value,System.currentTimeMillis());
		}else if("defaultModel".equals(type)){
			if("simple".equals(config.getDefaultModel())){
				value = "major";
			}else{
				value="simple";
			}
			configRepository.updateModelTypeById(id, value, System.currentTimeMillis());
		}else if("defaultFavorites".equals(type)){
			configRepository.updateFavoritesById(id, defaultFavorites,System.currentTimeMillis());
		}
	}

	@Override
	public Config findByUserId(Long userId) {
		return configRepository.findByUserId(userId);
	}

}
