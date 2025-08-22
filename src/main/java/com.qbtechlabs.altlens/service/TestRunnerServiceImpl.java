package com.qbtechlabs.altlens.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qbtechlabs.altlens.client.RestApiClient;
import com.qbtechlabs.altlens.model.InputRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TestRunnerServiceImpl implements TestRunnerService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestRunnerServiceImpl.class);
    private final RestApiClient restApiClient;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
    public TestRunnerServiceImpl(RestApiClient restApiClient) {
        this.restApiClient = restApiClient;
    }
    /**
     * This serice is responsible for executing tests by interacting with the RestApiClient.
     * It can be extended to include methods for running specific tests, retrieving results, etc.
     * Currently, it serves as a placeholder for future test execution logic.
     */
    @Override
    public String executeAndFetchResults(MultipartFile file, Model model) {
        List<InputRecord> inputRecords = new ArrayList<>();
        List<InputRecord> processedRecords = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                String apitype = (row.getCell(0) != null) ? row.getCell(0).getStringCellValue() : "";
                String method = (row.getCell(1) != null) ? row.getCell(1).getStringCellValue() : "";
                String apiurl = (row.getCell(2) != null) ? row.getCell(2).getStringCellValue() : "";
                String payload = (row.getCell(3) != null) ? row.getCell(3).getStringCellValue() : "";
                Map<String, Object> headersMap = null;
                Map<String, Object> paramsMap = null;

                try {
                    if (row.getCell(4) != null && !row.getCell(4).getStringCellValue().isEmpty()) {
                        headersMap = objectMapper.readValue(row.getCell(4).getStringCellValue(), Map.class);
                    }
                    if (row.getCell(5) != null && !row.getCell(5).getStringCellValue().isEmpty()) {
                        paramsMap = objectMapper.readValue(row.getCell(5).getStringCellValue(), Map.class);
                    }
                } catch (JsonProcessingException e) {
                    logger.error("Error parsing JSON in row {}: {}", row.getRowNum(), e.getMessage());
                }

                if (apitype == null && apiurl == null && method == null && payload == null && headersMap == null && paramsMap == null) {
                    logger.warn("Skipping empty record at row {}", row.getRowNum());
                    break; // Exit the loop
                }

                inputRecords.add(new InputRecord(apitype, method, apiurl, payload, headersMap, paramsMap, null, null, null));
            }

            // Process each record in parallel
            processedRecords = inputRecords.parallelStream()
                    .map(record -> {
                        try {
                            return restApiClient.decideCallAndCollectResponse(record.getApitype(), record.getMethod(),
                                    record.getApiurl(), record.getPayload(), record.getHeaders(), record.getParams());
                        } catch (Exception e) {
                            logger.error("Error processing record: {}", record, e);
                            return new InputRecord(record.getApitype(), record.getMethod(), record.getApiurl(),
                                    record.getPayload(), record.getHeaders(), record.getParams(),
                                    "500", "0", "Error: " + e.getMessage());
                        }
                    })
                    .collect(Collectors.toList());

            workbook.close();
        } catch (Exception exception) {
            logger.error("Error processing Excel file: ", exception);
        }
        int passed = (int) processedRecords.stream()
                .filter(record -> record.getResponseCode().equalsIgnoreCase("200") || record.getResponseCode().equalsIgnoreCase("200"))
                .count();
        int total = processedRecords.size();
        int failed = total - passed;
        double percentagePassed = total > 0 ? (passed * 100.0) / total : 0;

        model.addAttribute("passed", passed);
        model.addAttribute("failed", failed);
        model.addAttribute("percentagePassed", Math.round(percentagePassed));
        model.addAttribute("processedRecords", processedRecords);
        return "results";
    }
}
