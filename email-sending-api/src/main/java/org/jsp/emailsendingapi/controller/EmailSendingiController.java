package org.jsp.emailsendingapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.experimental.Helper;



@RestController
@RequestMapping("/api")
public class EmailSendingiController {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@PostMapping("/send-mail")
	public String sendEmail( @RequestParam String mailid) {
		MimeMessage message= javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		 
		
		try {
			helper.setTo(mailid);
			helper.setSubject("Testing the new Email sending API");
			helper.setText("we are sending this mail to test our Application");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		javaMailSender.send(message);
		return "Main has been send to"+mailid;
	}
}
