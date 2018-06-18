package com.favorites.favoriteswebdemo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.favorites.favoriteswebdemo.domain.Config;
import com.favorites.favoriteswebdemo.domain.Favorites;
import com.favorites.favoriteswebdemo.service.ConfigService;
import com.favorites.favoriteswebdemo.service.FavoritesService;

@Controller
public class IndexController extends BaseController{
	
	@Resource
	private ConfigService configService;
	@Resource
	private FavoritesService favoritesService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String regist() {
		return "register";
	}
	
	/**
	 * 登录后的主页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String home(Model model) {
		Config config = configService.findByUserId(getUserId());
		Favorites favorites = favoritesService.findById(Long.parseLong(config.getDefaultFavorties()));
		model.addAttribute("config",config);
		model.addAttribute("favorites",favorites);
		model.addAttribute("user",getUser());
		return "home";
	}
	
	/**
	 * 页面点击忘记密码连接，跳转到相应页面，填写接收验证的邮箱（用户注册时填写的邮箱）
	 * @return
	 */
	@RequestMapping(value="/forgotPassword",method=RequestMethod.GET)
	public String forgotPassword() {
		return "user/forgotpassword";
	}
	/**
	 * 重置密码连接--跳转到相应页面
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/newPassword",method=RequestMethod.GET)
	public String newPassword(String email) {
		return "user/newpassword";
	}
}
