package com.qbtechlabs.altlens.service.springAI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class SpringAIClientChat {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SpringAIClientChat.class);
    private final WebClient webClient;

    @Value("${gemini.api.endpoint}")
    private String geminiApiEndpoint;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public SpringAIClientChat(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * This method generates test cases in plain text and TestNG format for a given API.
     *
     * @param apiDetails The details of the API (e.g., method, URL, payload).
     * @return A Map containing plain text and TestNG test cases.
     */
    public Map<String, String> generateTestCases(String apiDetails) {
        logger.info("Calling Gemini API to generate test cases for API: {}", apiDetails);
        try {
            // Construct the prompt
            String prompt = "Generate test cases for the following API in two formats:\n" +
                    "1. Plain text test case.\n" +
                    "2. TestNG test case.\n" +
                    "API Details:\n" + apiDetails;

            // Call Gemini API endpoint
            String response = webClient.post()
                    .uri(geminiApiEndpoint)
                    .header("x-goog-api-key", geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(Map.of(
                            "contents", new Object[]{
                                    Map.of("parts", new Object[]{
                                            Map.of("text", prompt)
                                    })
                            }
                    ))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            logger.info("Received response from Gemini API: {}", response);

            // Parse the response to extract plain text and TestNG test cases
            String plainTextTestCase = extractPlainTextTestCase(response);
            String testNGTestCase = extractTestNGTestCase(apiDetails);

            return Map.of(
                    "plainText", plainTextTestCase,
                    "testNG", testNGTestCase
            );
        } catch (Exception e) {
            logger.error("Error calling Gemini API: {}", e.getMessage());
            return Map.of(
                    "plainText", "Error: Failed to generate plain text test case.",
                    "testNG", "Error: Failed to generate TestNG test case."
            );
        }
    }

    private String extractPlainTextTestCase(String response) {
        try {
            // Parse the response JSON to extract the plain text test case
            var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);

            // Extract the plain text test case
            if (responseMap.containsKey("plainTextTestCase")) {
                return responseMap.get("plainTextTestCase").toString();
            } else {
                logger.warn("Key 'plainTextTestCase' not found in the response.");
                return "Error: 'plainTextTestCase' key not found in the response.";
            }
        } catch (Exception e) {
            logger.error("Error parsing response to extract plain text test case: {}", e.getMessage());
            return "Error: Failed to parse response.";
        }
    }

    private String extractTestNGTestCase(String apiDetails) {
        try {
            // Parse the apiDetails string (assumed to be JSON format)
            var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            var apiDetailsMap = objectMapper.readValue(apiDetails, Map.class);

            // Extract API details
            var method = apiDetailsMap.getOrDefault("method", "GET").toString();
            var url = apiDetailsMap.getOrDefault("url", "").toString();
            var payload = apiDetailsMap.getOrDefault("payload", "").toString();
            var headers = (Map<String, String>) apiDetailsMap.getOrDefault("headers", Map.of());

            // Construct TestNG test case
            var headersCode = headers.entrySet().stream()
                    .map(entry -> ".header(\"" + entry.getKey() + "\", \"" + entry.getValue() + "\")")
                    .reduce("", (a, b) -> a + b);

            return """
                import org.testng.annotations.Test;
                import io.restassured.RestAssured;
                import io.restassured.response.Response;

                public class APITest {
                    @Test
                    public void testAPI() {
                        // Make API call
                        Response response = RestAssured.given()
                            %s
                            .body(%s)
                            .%s("%s");

                        // Validate response
                        response.then().statusCode(200);
                        System.out.println("Response: " + response.getBody().asString());
                    }
                }
            """.formatted(headersCode, payload.isEmpty() ? "\"\"" : "\"" + payload + "\"", method.toLowerCase(), url);
        } catch (Exception e) {
            logger.error("Error parsing apiDetails to generate TestNG test case: {}", e.getMessage());
            return "Error: Failed to generate TestNG test case.";
        }
    }
}