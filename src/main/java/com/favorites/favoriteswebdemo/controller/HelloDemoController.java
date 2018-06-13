package com.favorites.favoriteswebdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloDemoController {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
