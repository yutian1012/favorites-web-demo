package com.favorites.favoriteswebdemo.service;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.favorites.favoriteswebdemo.util.MessageUtil;

@Component
public class MailServiceImpl implements MailService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
    private JavaMailSender mailSender;
	
	@Resource
	private SpringTemplateEngine springTemplateEngine;

    @Value("${mail.fromMail.addr}")
    private String from;
	@Value("${mail.subject.forgotpassword}")
	private String mailSubject;
	@Value("${mail.content.forgotpassword}")
	private String mailContent;
	@Value("${forgotpassword.url}")
	private String forgotpasswordUrl;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }
    }

	@Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }

	@Override
	public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
		MimeMessage message = mailSender.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);
	        FileSystemResource file = new FileSystemResource(new File(filePath));
	        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
	        helper.addAttachment(fileName, file);
	        mailSender.send(message);
	        logger.info("带附件的邮件已经发送。");
	    } catch (MessagingException e) {
	        logger.error("发送带附件的邮件时发生异常！", e);
	    }
	}

	@Override
	public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
		MimeMessage message = mailSender.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);
	        FileSystemResource res = new FileSystemResource(new File(rscPath));
	        helper.addInline(rscId, res);
	        mailSender.send(message);
	        logger.info("嵌入静态资源的邮件已经发送。");
	    } catch (MessagingException e) {
	        logger.error("发送嵌入静态资源的邮件时发生异常！", e);
	    }
	}

	@Override
	public void sendTemplateMail(String templateName,Map<String,Object> params,String to, String subject) {
		Context context = new Context();
		if(null!=params) {
			context.setVariables(params);
		}
	    String emailContent = springTemplateEngine.process(templateName, context);
	    sendHtmlMail(to, subject, emailContent);
	}

	@Override
	public void sendForgotPasswordEmail(String digitalSignature,String to) {
        
        String resetPassHref = forgotpasswordUrl + "?sid="+ digitalSignature +"&email="+to;
        String emailContent = MessageUtil.getMessage(mailContent, resetPassHref);//替换连接
        
        MimeMessage mimeMessage = mailSender.createMimeMessage();	
        try {
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(mailSubject);
	        helper.setText(emailContent, true);
	        mailSender.send(mimeMessage);
        }catch (MessagingException e) {
	        logger.error("邮件发送发生异常！", e);
	    }
	}
}
