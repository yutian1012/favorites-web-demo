package com.favorites.favoriteswebdemo.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {
	
	@Resource
	private MailService mailService;

	@Test
	public void testSendSimpleMail() {
		mailService.sendSimpleMail("yutian1012@126.com", "邮件发送测试", "测试！！！");
	}
	
	@Test
	public void testSendHtmlMail() {
		String content="<html>\n" +
            "<body>\n" +
            "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
            "</body>\n" +
            "</html>";
		mailService.sendHtmlMail("yutian1012@126.com","test simple mail",content);
	}
	
	@Test
	public void testSendAttachmentsMail() {
		String filePath="C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg";
	    mailService.sendAttachmentsMail("yutian1012@126.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
	}
	
	@Test
	public void testSendInlineResourceMail() {
		String rscId = "neo006";
	    String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
	    String imgPath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg";
	    mailService.sendInlineResourceMail("yutian1012@126.com", "主题：这是有图片的邮件", content, imgPath, rscId);
	}
	
	@Test
	public void testSendTemplateMail() {
		Map<String,Object> context=new HashMap<>();
		context.put("id", "006");
		mailService.sendTemplateMail("emailTemplate", context,"yutian1012@126.com", "主题：这是模板邮件");
	}
}
