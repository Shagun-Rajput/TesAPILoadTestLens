package com.qbtechlabs.altlens.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author shagun.rajput
 */
@Configuration
public class AltLensConfig {
    /*******************************************************************************************************************
     * Bean for WebClient to make HTTP requests.
     * This can be used for health checks and other HTTP interactions.
     *
     * @return a configured WebClient instance
     ******************************************************************************************************************/
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
