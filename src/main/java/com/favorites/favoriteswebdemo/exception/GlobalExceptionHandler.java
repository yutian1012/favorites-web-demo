package com.favorites.favoriteswebdemo.exception;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.favorites.favoriteswebdemo.common.ExceptionMsg;
import com.favorites.favoriteswebdemo.common.Response;
import com.favorites.favoriteswebdemo.view.JacksonView;

@ControllerAdvice
public class GlobalExceptionHandler {

	protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView defaultErrorHandler(Exception e, HttpServletRequest request) throws Exception {
        logger.info("请求地址：" + request.getRequestURL());
        //rlogger.error("异常信息：",e);
        
        ModelAndView mav = new ModelAndView();
        
        if (isAjax(request)) {
            JacksonView view = new JacksonView();
            view.addStaticAttribute("data", new Response(ExceptionMsg.FAILED));
            mav.setView(view);
        } else {
            mav.setViewName(DEFAULT_ERROR_VIEW);
        }
        
        return mav;
    }
    /**
     * 判断请求是否为ajax请求
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}
