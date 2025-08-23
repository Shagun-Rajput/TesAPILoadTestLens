package com.qbtechlabs.altlens.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.qbtechlabs.altlens.client.RestApiClient;
import com.qbtechlabs.altlens.model.InputRecord;
import com.qbtechlabs.altlens.service.springAI.SpringAIClientChat;
import com.qbtechlabs.altlens.service.swaggerutil.SwaggerUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AITestRunnerServiceImpl implements AITestRunnerService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AITestRunnerServiceImpl.class);
    private final SwaggerUtil swaggerUtil;
    private final SpringAIClientChat springAIClientChat;
    private final RestApiClient restApiClient;
    public AITestRunnerServiceImpl(SwaggerUtil swaggerUtil,
                                   SpringAIClientChat springAIClientChat,
                                   RestApiClient restApiClient) {
        this.swaggerUtil = swaggerUtil;
        this.springAIClientChat = springAIClientChat;
        this.restApiClient = restApiClient;
    }
    /**
     * This service is responsible for executing AI tests by processing Swagger data.
     * It includes methods to extract the Swagger URL, fetch the Swagger JSON,
     * and parse the Swagger data into a list of records.
     */

    @Override
    public String runAITests(String inputJson, Model model) {
        try {
            // Step 1: Extract Swagger URL
            String swaggerUrl = swaggerUtil.extractSwaggerUrl(inputJson, model);
            if (swaggerUrl == null) return "airesults";

            // Step 2: Fetch Swagger JSON
            JsonNode swaggerJson = swaggerUtil.fetchSwaggerJson(swaggerUrl);
            if (swaggerJson == null) {
                model.addAttribute("error", "Failed to fetch Swagger data.");
                return "airesults";
            }
            logger.info("Service: Successfully fetched Swagger JSON from URL: [{}]", swaggerUrl);
            // Step 3: Parse Swagger Data
            List<Map<String, Object>> apiDetails = swaggerUtil.parseSwaggerData(swaggerJson);
            // Step 4: Generate and Execute Test Cases
            List<InputRecord> processedRecords = new ArrayList<>();
            for (Map<String, Object> apiDetail : apiDetails) {
                // Generate test case prompt
                String prompt = generateTestCasePrompt(apiDetail);

                // Use Spring AI to generate test case
                String testCase = springAIClientChat.generateTestCase(prompt);
                logger.info("Generated Test Case: {}", testCase);

                // Execute test case using RestApiClient
                InputRecord result = restApiClient.decideCallAndCollectResponse(
                        apiDetail.get("apitype").toString(),
                        apiDetail.get("method").toString(),
                        apiDetail.get("apiurl").toString(),
                        apiDetail.get("payload").toString(),
                        (Map<String, Object>) apiDetail.get("headers"),
                        (Map<String, Object>) apiDetail.get("params")
                );
                processedRecords.add(result);
            }

            // Step 5: Add Results to Model
            model.addAttribute("processedRecords", processedRecords);
            logger.info("Service: AI Test run completed with {} records", processedRecords.size());

            return "airesults";
        } catch (Exception exception) {
            logger.error("Error processing Swagger data: {}", exception.getMessage());
            model.addAttribute("error", "An error occurred while processing Swagger data.");
            return "airesults";
        }
    }

    private String generateTestCasePrompt(Map<String, Object> apiDetail) {
        return String.format(
                "Generate a test case for the following API:\n" +
                        "Method: %s\n" +
                        "URL: %s\n" +
                        "Payload: %s\n" +
                        "Headers: %s\n" +
                        "Parameters: %s",
                apiDetail.get("method"),
                apiDetail.get("apiurl"),
                apiDetail.get("payload"),
                apiDetail.get("headers"),
                apiDetail.get("params")
        );
    }


}