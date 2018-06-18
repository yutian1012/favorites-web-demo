package com.favorites.favoriteswebdemo.controller;

import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.favoriteswebdemo.common.Const;
import com.favorites.favoriteswebdemo.common.ExceptionMsg;
import com.favorites.favoriteswebdemo.common.Response;
import com.favorites.favoriteswebdemo.domain.User;
import com.favorites.favoriteswebdemo.service.MailService;
import com.favorites.favoriteswebdemo.service.UserService;
import com.favorites.favoriteswebdemo.util.MD5Util;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Resource
	private UserService userService;
	@Resource
	private MailService mailService;
	
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
			user.setPassWord(getPwd(user.getPassWord()));//暂时存储为明文，后期可以对其进行加密处理
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
			}else if(!loginUser.getPassWord().equals(getPwd(user.getPassWord()))){
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
	 * 发送重置密码邮件
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/sendForgotPasswordEmail", method = RequestMethod.POST)
	public Response sendForgotPasswordEmail(String email) {
		try {
			User registUser = userService.findByEmail(email);
			if (null == registUser) {
				return result(ExceptionMsg.EmailNotRegister);
			}
			String secretKey = UUID.randomUUID().toString(); // 密钥
            Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
            long date = outDate.getTime() / 1000 * 1000;
            userService.setOutDateAndValidataCode(outDate+"", secretKey, email);
            
            mailService.sendForgotPasswordEmail(MD5Util.encrypt(email + "$" + date + "$" + secretKey), email);
            
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("sendForgotPasswordEmail failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result(ExceptionMsg.SUCCESS);
	}
	
	/**
	 * 忘记密码-设置新密码
	 * @param newpwd
	 * @param email
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/setNewPassword", method = RequestMethod.POST)
	public Response setNewPassword(String newpwd, String email, String sid) {
		try {
			User user = userService.findByEmail(email);
			Timestamp outDate = Timestamp.valueOf(user.getOutDate());
			if(outDate.getTime() <= System.currentTimeMillis()){ //表示已经过期
				return result(ExceptionMsg.LinkOutdated);
            }
            String key = user.getEmail()+"$"+outDate.getTime()/1000*1000+"$"+user.getValidataCode();//数字签名
            String digitalSignature = MD5Util.encrypt(key);
            if(!digitalSignature.equals(sid)) {
            	 return result(ExceptionMsg.LinkOutdated);
            }
            userService.setNewPassword(getPwd(newpwd), email);
		} catch (Exception e) {
			logger.error("setNewPassword failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result(ExceptionMsg.SUCCESS);
	}
	
	protected String getPwd(String password){
    	try {
    		String pwd = MD5Util.encrypt(password+Const.PASSWORD_KEY);
    		return pwd;
		} catch (Exception e) {
			logger.error("密码加密异常：",e);
		}
    	return null;
    }
}
