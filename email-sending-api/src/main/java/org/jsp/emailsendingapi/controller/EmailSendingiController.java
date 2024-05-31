package org.jsp.emailsendingapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class EmailSendingiController {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("$(application.token)")
	private String token;

	@PostMapping("/send-mail")
	public String sendEmail(@RequestParam String mailid, HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String path = request.getServletPath();
		String activation_link = url.replace(path, "/api/activate");
		activation_link = activation_link + "?token=" + token;
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(mailid);
			helper.setSubject("Testing the new Email sending API");
			helper.setText("Activate yout Account by clicking the following URL:"+ activation_link);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		javaMailSender.send(message);
		return "Mail  has been send to" + mailid;
	}

	@PostMapping("/activate")
	public String sendtoken(@RequestParam String token) {
		if (token.equals(this.token)) {
			return " your account has been activated";
		}
		return " cannot accivate your account because verification token invalid";
	}
}
