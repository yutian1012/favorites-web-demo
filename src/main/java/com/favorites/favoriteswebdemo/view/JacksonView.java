package com.favorites.favoriteswebdemo.view;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        try(ByteArrayOutputStream outnew = new ByteArrayOutputStream();ServletOutputStream out = response.getOutputStream();){
        	ObjectMapper mapper = new ObjectMapper();  
        	String json = mapper.writeValueAsString(model);  
        	
        	int len=json.getBytes().length;
        	
        	outnew.write(json.getBytes());
        	
        	response.setContentLength(len);
        	
        	outnew.writeTo(out);
        	outnew.close();
        	out.flush();
        };
	}
}
