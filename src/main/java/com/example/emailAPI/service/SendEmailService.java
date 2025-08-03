package com.example.emailAPI.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.samskivert.mustache.Mustache;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private Mustache.Compiler mustacheCompiler;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    public void sendEmail(String recipient, String subject) {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/email.mustache");
            Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String template = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));

            Map<String, Object> model = new HashMap<>();
            model.put("username", "Daksh");
            model.put("applicationId", "APP123456");

            String htmlContent = mustacheCompiler.compile(template).execute(model);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmailId);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (MessagingException | java.io.IOException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
