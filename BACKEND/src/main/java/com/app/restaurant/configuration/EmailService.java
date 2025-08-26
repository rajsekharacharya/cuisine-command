package com.app.restaurant.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

/**
 * EmailService - Service for sending emails asynchronously.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * Send an email to a single recipient.
     *
     * @param to      Recipient email address
     * @param subject Subject of the email
     * @param body    HTML body of the email
     */
    @Async
    public void sendEmail(String to, String subject, String body) {
        try {
            if (to == null || to.isEmpty()) {
                log.warn("Recipient email address is missing.");
                return;
            }

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(message);
            log.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage(), e);
        }
    }

    /**
     * Send an email to a single recipient with CC addresses.
     *
     * @param to      Recipient email address
     * @param cc      CC email addresses
     * @param subject Subject of the email
     * @param body    HTML body of the email
     */
    @Async
    public void sendEmail(String to, String[] cc, String subject, String body) {
        try {
            if (to == null || to.isEmpty()) {
                log.warn("Recipient email address is missing.");
                return;
            }

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmail);
            helper.setTo(to);
            if (cc != null && cc.length > 0) {
                helper.setCc(cc);
            }
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(message);
            log.info("Email sent successfully to {}", to);
            if (cc != null && cc.length > 0) {
                log.info("CC: {}", String.join(", ", cc));
            }
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage(), e);
        }
    }
}
