package com.qbtechlabs.altlens;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AltLens {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AltLens.class);
    public static void main(String[] args) {
        SpringApplication.run(AltLens.class, args);
        logger.info("*******************************************************************************");
        logger.info("*************************** Altlens started successfully **********************");
        logger.info("*** Please visit -> http://localhost:8500/dashboard to access the dashboard ***");
        logger.info("*******************************************************************************");
    }
}