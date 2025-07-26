package com.example.emailAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.emailAPI.service.SendEmailService;

@RestController
public class EmailController {
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@GetMapping("sendEmail")
	public String sendEmail() {
		sendEmailService.sendEmail("dakshchoudhary.844@gmail.com", "Test Email", "Spring Mail");
		
		return "Successfully Sent";
	}

}
