package com.favorites.favoriteswebdemo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.favorites.favoriteswebdemo.common.Const;
import com.favorites.favoriteswebdemo.common.ExceptionMsg;
import com.favorites.favoriteswebdemo.common.Response;
import com.favorites.favoriteswebdemo.domain.User;

public class BaseController {

	protected User getUser() {
        return (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
    }
	
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	protected HttpSession getSession() {
        return getRequest().getSession();
    }
	
	protected Response result(ExceptionMsg msg){
    	return new Response(msg);
    }
	
	protected long getUserId() {
    	Long id=0l;
    	User user=getUser();
    	if(user!=null){
    		id=user.getId();
    	}
        return id;
    }
	
	/**
	 * 将登录用户信息保存到session中
	 * @param user
	 */
	protected void saveSession(User user) {
		HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		request.getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
	}
}
