package com.favorites.favoriteswebdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
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
		return "home";
	}
}
