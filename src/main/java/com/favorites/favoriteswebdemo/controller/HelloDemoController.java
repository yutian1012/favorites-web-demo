package com.favorites.favoriteswebdemo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloDemoController {
	
	@RequestMapping("/index")
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
	
	/**
	 * 跳转到order页面
	 * @return
	 */
	@RequestMapping("order")
	public String order() {
		return "order";
	}
	/**
	 * 模拟提交订单页面
	 * @param model
	 * @return
	 */
	@RequestMapping("submitOrder")
	public String submitOrder(Model model) {
		System.out.println("提交订单成功");
		model.addAttribute("oper", "SUCCESS");
		return "orderResult";
	}
	/**
	 * ajax方式提交
	 * @return
	 */
	@RequestMapping("ajaxSubmitOrder")
	@ResponseBody
	public Map<String,String> ajaxSubmitOrder(){
		System.out.println("提交订单成功");
		Map<String,String> result=new HashMap<>();
		result.put("result", "SUCCESS");
		return result;
	}
	
}
