# Spring-Email

## Mail Server Properties

The Java Mail support in Spring is organized as follows:

| Interface / Class      | Description                                                                                       |
|------------------------|---------------------------------------------------------------------------------------------------|
| MailSender             | Top-level interface providing basic functionality for sending simple emails.                     |
| JavaMailSender         | Subinterface of MailSender that supports MIME messages; commonly used with MimeMessageHelper.     |
| JavaMailSenderImpl     | Default implementation of JavaMailSender; supports both MimeMessage and SimpleMailMessage.        |
| SimpleMailMessage      | Class for creating simple mail messages (from, to, cc, subject, text).                            |
| MimeMessagePreparator  | Callback interface for preparing complex MIME messages before sending.                            |
| MimeMessageHelper      | Helper class for building MIME messages with attachments, inline images, and HTML content.       |

Mustache Implementation in feature/mustache-implementation
