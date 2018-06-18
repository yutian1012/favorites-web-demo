package com.favorites.favoriteswebdemo.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.favorites.favoriteswebdemo.common.Const;

/**
 * 安全访问过滤器
 */
public class SecurityFilter implements Filter{
	protected Logger logger =  LoggerFactory.getLogger(this.getClass());
	//可以匿名访问的连接地址
	private static Set<String> GreenUrlSet = new HashSet<String>();
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		GreenUrlSet.add("/login");
		GreenUrlSet.add("/register");
		GreenUrlSet.add("/index");
		GreenUrlSet.add("/forgotPassword");
		GreenUrlSet.add("/newPassword");
		GreenUrlSet.add("/tool");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		if (req.getSession().getAttribute(Const.LOGIN_SESSION_KEY) == null) {
			//判断访问的地址是否允许匿名访问
			if (containsSuffix(uri)  || GreenUrlSet.contains(uri)|| containsKey(uri)) {
				logger.debug("don't check  url , " + uri);
				chain.doFilter(request, response);
				return;
			}
			//请求转发到登录页面r
			req.getRequestDispatcher("/login").forward(request,response);
			
			return;
		}
		
		chain.doFilter(request, response);
	}
	
	/**
	 * 过滤静态资源
	 */
	private boolean containsSuffix(String url) {
		if (url.endsWith(".js")
				|| url.endsWith(".css")
				|| url.endsWith(".jpg")
				|| url.endsWith(".gif")
				|| url.endsWith(".png")
				|| url.endsWith(".html")
				|| url.endsWith(".eot")
				|| url.endsWith(".svg")
				|| url.endsWith(".ttf")
				|| url.endsWith(".woff")
				|| url.endsWith(".ico")
				|| url.endsWith(".woff2")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @param url
	 * @return
	 * @author neo
	 * @date 2016-5-4
	 */
	private boolean containsKey(String url) {

		if (url.contains("/media/")
				|| url.contains("/login")||url.contains("/user/login")
				|| url.contains("/register")||url.contains("/user/regist")||url.contains("/index")
				|| url.contains("/forgotPassword")||url.contains("/user/sendForgotPasswordEmail")
				|| url.contains("/newPassword")||url.contains("/user/setNewPassword")
				|| (url.contains("/collector") && !url.contains("/collect/detail/"))
				|| url.contains("/collect/standard/")||url.contains("/collect/simple/")
				|| url.contains("/user")||url.contains("/favorites")||url.contains("/comment")
				|| url.startsWith("/lookAround/standard/")
				|| url.startsWith("/lookAround/simple/")
				|| url.startsWith("/user/")
				|| url.startsWith("/feedback")
				|| url.startsWith("/standard/")
				|| url.startsWith("/collect/standard/lookAround/")
				|| url.startsWith("/collect/simple/lookAround/")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	@Override
	public void destroy() {
	}

}
