package com.example.emailAPI.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    public String sendEmail(String recipient, String subject) {
        try {
            // Load and render Mustache template
            MustacheFactory mf = new DefaultMustacheFactory();
            InputStreamReader templateReader = new InputStreamReader(
                new ClassPathResource("templates/format.html").getInputStream()
            );
            Mustache mustache = mf.compile(templateReader, "format");

            // Data for the placeholders
            Map<String, Object> model = new HashMap<>();
            model.put("username", "Daksh");
            model.put("applicationId", "APP123456");

            StringWriter writer = new StringWriter();
            mustache.execute(writer, model).flush();
            String htmlContent = writer.toString();

            // Send email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmailId);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // HTML enabled

            javaMailSender.send(message);
            return htmlContent;
        } catch (MessagingException | java.io.IOException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
