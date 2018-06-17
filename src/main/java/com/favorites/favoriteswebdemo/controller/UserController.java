package com.favorites.favoriteswebdemo.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.favorites.favoriteswebdemo.common.Const;
import com.favorites.favoriteswebdemo.common.ExceptionMsg;
import com.favorites.favoriteswebdemo.common.Response;
import com.favorites.favoriteswebdemo.domain.User;
import com.favorites.favoriteswebdemo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	protected Logger logger =  LoggerFactory.getLogger(this.getClass());
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public Response regist(User user) {
		try {
			User registUser = userService.findByEmail(user.getEmail());
			if (null != registUser) {
				return result(ExceptionMsg.EmailUsed);
			}
			User userNameUser = userService.findByUserName(user.getUserName());
			if (null != userNameUser) {
				return result(ExceptionMsg.UserNameUsed);
			}
			//user.setPassWord(getPwd(user.getPassWord()));//暂时存储为明文，后期可以对其进行加密处理
			user.setCreateTime(System.currentTimeMillis());
			user.setLastModifyTime(System.currentTimeMillis());
			user.setProfilePicture("img/favicon.png");
			userService.save(user);
		} catch (Exception e) {
			logger.error("create user failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return new Response();
	}
	
	protected Response result(ExceptionMsg msg){
    	return new Response(msg);
    }
	/**
	 * 登录
	 * @param user
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response login(User user,HttpServletResponse response) {
		try {
			//这里不是bug，前端userName有可能是邮箱也有可能是昵称。
			User loginUser = userService.findByUserNameOrEmail(user.getUserName(), user.getUserName());
			if (loginUser == null ) {
				return new Response(ExceptionMsg.LoginNameNotExists);
			}else if(!loginUser.getPassWord().equals(user.getPassWord())){
				return new Response(ExceptionMsg.LoginNameOrPassWordError);
			}
			
			String preUrl = "/";
			
			Response res=new Response(ExceptionMsg.SUCCESS);
			res.setUrl(preUrl);
			
			saveSession(loginUser);
			
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("login failed, ", e);
			return new Response(ExceptionMsg.FAILED);
		}
	}
	/**
	 * 将登录用户信息保存到session中
	 * @param user
	 */
	public void saveSession(User user) {
		HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		request.getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
	}
	
}
