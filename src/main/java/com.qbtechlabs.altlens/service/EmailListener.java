package com.qbtechlabs.altlens.service;

import org.slf4j.Logger;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailListener implements MessageListener{
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EmailListener.class);
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                logger.info("Processing email: {}", textMessage.getText());
                // Add email sending logic here
            }
        } catch (Exception e) {
            logger.error("Error processing message: ", e);
        }
    }
}
