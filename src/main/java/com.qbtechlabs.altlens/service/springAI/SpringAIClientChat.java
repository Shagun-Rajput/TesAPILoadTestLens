package com.qbtechlabs.altlens.service.springAI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class SpringAIClientChat {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SpringAIClientChat.class);
    private final WebClient webClient;

    @Value("${spring.ai.endpoint}")
    private String springAiEndpoint;

    @Value("${spring.ai.api-key}")
    private String apiKey;

    public SpringAIClientChat(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    /**
     * This method generates a test case by calling the Spring AI endpoint with the provided prompt.
     * It handles the response and errors appropriately, logging the process.
     *
     * @param prompt The input prompt for generating the test case.
     * @return The generated test case as a String.
     */
    public String generateTestCase(String prompt) {
        logger.info("Calling Spring AI with prompt: {}", prompt);
        try {
            // Call Spring AI endpoint
            String response = webClient.post()
                    .uri(springAiEndpoint)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(Map.of("prompt", prompt))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            logger.info("Received response from Spring AI: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Error calling Spring AI: {}", e.getMessage());
            return "{ \"error\": \"Failed to generate test case.\" }";
        }
    }
}