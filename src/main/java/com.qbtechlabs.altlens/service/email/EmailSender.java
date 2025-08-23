package com.qbtechlabs.altlens.service.email;

import com.qbtechlabs.altlens.model.EmailMessage;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EmailSender.class);
    @Autowired
    private JavaMailSender mailSender;
    public void processEmail(EmailMessage emailMessage) {
        try {
            logger.info("Processing email for: {}", emailMessage.getEmail());
            // Create a MIME message
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(emailMessage.getEmail());
            helper.setSubject("ALT Lens Test Results Report");
            helper.setText("Please find the attached test results report.", true);
            // Attach the Excel file
            helper.addAttachment(emailMessage.getFileName(),
                    new org.springframework.core.io.ByteArrayResource(emailMessage.getAttachmentData()));
            // Send the email
            mailSender.send(mimeMessage);
            logger.info("Email sent to: {}", emailMessage.getEmail());
        } catch (Exception e) {
            logger.error("Error processing email for {}: ", emailMessage.getEmail(), e);
        }
    }
}