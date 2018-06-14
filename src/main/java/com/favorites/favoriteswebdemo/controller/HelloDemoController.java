package com.favorites.favoriteswebdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloDemoController {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	/**
	 * 用户的主页
	 * @return
	 */
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
	
	
	/**
	 * 直接访问布局页面
	 * @return
	 */
	@RequestMapping("/layout")
	public String layout() {
		return "fragments/layout";
	}
	
	
}
