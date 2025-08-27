package com.qbtechlabs.altlens.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.qbtechlabs.altlens.client.RestApiClient;
import com.qbtechlabs.altlens.model.InputRecord;
import com.qbtechlabs.altlens.service.springAI.SpringAIClientChat;
import com.qbtechlabs.altlens.service.swaggerutil.SwaggerUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
                // Use Spring AI to generate test cases
                Map<String, String> testCases = springAIClientChat.generateTestCases(apiDetail.toString());
                logger.info("Generated Test Cases: {}", testCases);

                // Add plainText test cases to the sheet/record
                String plainTextTestCase = testCases.get("plainText");
                if (plainTextTestCase != null) {
                    logger.info("Adding plainText test case to record: {}", plainTextTestCase);
                    // Add plainText test case to the record (e.g., write to a sheet or log it)
                    // Assume a method `addToSheet` exists for this purpose
                    addToSheet(apiDetail, plainTextTestCase);
                }

                // Execute TestNG test case and store the result
                String testNGTestCase = testCases.get("testNG");
                if (testNGTestCase != null) {
                    logger.info("Executing TestNG test case: {}", testNGTestCase);
                    InputRecord result = restApiClient.decideCallAndCollectResponse(
                            apiDetail.get("apitype").toString(),
                            apiDetail.get("method").toString(),
                            apiDetail.get("apiurl").toString(),
                            apiDetail.get("payload").toString(),
                            (Map<String, Object>) apiDetail.get("headers"),
                            (Map<String, Object>) apiDetail.get("params")
                    );
                    apiDetail.put("testNGResult", result); // Add the result to the record
                    processedRecords.add(result);
                }
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

    private void addToSheet(Map<String, Object> apiDetail, String plainTextTestCase) {
        try {
            // Create or open an Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.getSheet("TestCases");
            if (sheet == null) {
                sheet = workbook.createSheet("TestCases");
            }

            // Find the next empty row
            int rowCount = sheet.getLastRowNum();
            if (rowCount == 0 && sheet.getRow(0) == null) {
                // Add headers if the sheet is empty
                var headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("API Type");
                headerRow.createCell(1).setCellValue("Method");
                headerRow.createCell(2).setCellValue("URL");
                headerRow.createCell(3).setCellValue("Payload");
                headerRow.createCell(4).setCellValue("Headers");
                headerRow.createCell(5).setCellValue("Params");
                headerRow.createCell(6).setCellValue("PlainText Test Case");
            }

            // Add the API details and plainText test case to the next row
            var row = sheet.createRow(rowCount + 1);
            row.createCell(0).setCellValue(apiDetail.getOrDefault("apitype", "Unknown").toString());
            row.createCell(1).setCellValue(apiDetail.getOrDefault("method", "Unknown").toString());
            row.createCell(2).setCellValue(apiDetail.getOrDefault("apiurl", "Unknown").toString());
            row.createCell(3).setCellValue(apiDetail.getOrDefault("payload", "Unknown").toString());
            row.createCell(4).setCellValue(apiDetail.getOrDefault("headers", "Unknown").toString());
            row.createCell(5).setCellValue(apiDetail.getOrDefault("params", "Unknown").toString());
            row.createCell(6).setCellValue(plainTextTestCase);

            // Save the workbook to a file
            try (var fileOut = new java.io.FileOutputStream("TestCases.xlsx")) {
                workbook.write(fileOut);
            }
            workbook.close();

            logger.info("Successfully added plainText test case and details to sheet.");
        } catch (Exception e) {
            logger.error("Error adding plainText test case to sheet: {}", e.getMessage());
        }
    }
}