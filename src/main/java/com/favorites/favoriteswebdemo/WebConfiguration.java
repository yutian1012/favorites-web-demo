package com.favorites.favoriteswebdemo;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.favorites.favoriteswebdemo.filter.SecurityFilter;

@Configuration
public class WebConfiguration {
	//配置filter过滤器
	 @Bean
	 public FilterRegistrationBean<Filter> filterRegistration() {
		 FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
         registration.setFilter(new SecurityFilter());
         registration.addUrlPatterns("/*");
         //registration.addInitParameter("paramName", "paramValue");
         registration.setName("MyFilter");
         registration.setOrder(1);
         return registration;
	 }
}
