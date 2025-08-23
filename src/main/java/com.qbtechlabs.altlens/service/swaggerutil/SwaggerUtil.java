package com.qbtechlabs.altlens.service.swaggerutil;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.qbtechlabs.altlens.constants.Constants.OBJECT_MAPPER;

@Service
public class SwaggerUtil {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SwaggerUtil.class);
    /**
     * This service is responsible for processing Swagger JSON data.
     * It includes methods to extract the Swagger URL, fetch the Swagger JSON,
     * and parse the Swagger data into a list of records.
     */
    public String extractSwaggerUrl(String inputJson, Model model) {
        try {
            Map<String, String> inputMap = OBJECT_MAPPER.readValue(inputJson, Map.class);
            String swaggerUrl = inputMap.get("swaggerUrl");
            if (swaggerUrl == null || swaggerUrl.isBlank()) {
                logger.error("Invalid input: swaggerUrl is missing or empty");
                model.addAttribute("error", "Invalid input: swaggerUrl is required.");
                return null;
            }
            return swaggerUrl;
        } catch (Exception exception) {
            logger.error("Error parsing input JSON: {}", exception.getMessage());
            model.addAttribute("error", "Invalid input format.");
            return null;
        }
    }

    public JsonNode fetchSwaggerJson(String swaggerUrl) {
        try {
            URL url = new URL(swaggerUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                return OBJECT_MAPPER.readTree(connection.getInputStream());
            } else {
                logger.error("Failed to fetch Swagger JSON. HTTP response code: {}", connection.getResponseCode());
                return null;
            }
        } catch (Exception exception) {
            logger.error("Error fetching Swagger JSON: {}", exception.getMessage());
            return null;
        }
    }

    public List<Map<String, Object>> parseSwaggerData(JsonNode swaggerJson) {
        List<Map<String, Object>> processedRecords = new ArrayList<>();
        try {
            JsonNode paths = swaggerJson.get("paths");
            if (paths != null) {
                paths.fields().forEachRemaining(pathEntry -> {
                    String apiUrl = pathEntry.getKey();
                    JsonNode methods = pathEntry.getValue();
                    methods.fields().forEachRemaining(methodEntry -> {
                        String method = methodEntry.getKey().toUpperCase();
                        JsonNode details = methodEntry.getValue();
                        Map<String, Object> record = Map.of(
                                "apitype", "REST",
                                "method", method,
                                "apiurl", apiUrl,
                                "payload", details.has("requestBody") ? details.get("requestBody").toString() : "{}",
                                "headers", Map.of(), // Add logic to extract headers if available
                                "params", details.has("parameters") ? details.get("parameters").toString() : "[]",
                                "responseCode", "N/A", // Placeholder
                                "responseTime", "N/A", // Placeholder
                                "responseMessage", "N/A" // Placeholder
                        );
                        processedRecords.add(record);
                    });
                });
            }
        } catch (Exception exception) {
            logger.error("Error parsing Swagger data: {}", exception.getMessage());
        }
        return processedRecords;
    }
}
