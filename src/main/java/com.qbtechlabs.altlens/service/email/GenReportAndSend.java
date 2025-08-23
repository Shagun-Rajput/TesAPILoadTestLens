package com.qbtechlabs.altlens.service.email;

import com.qbtechlabs.altlens.model.EmailMessage;
import com.qbtechlabs.altlens.model.InputRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class GenReportAndSend {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GenReportAndSend.class);
     private final EmailSender emailSender;
    public GenReportAndSend(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    /**
     * This service generates an Excel report from the processed records and sends it via email.
     * It uses Apache POI to create the Excel file and JMS to send the email with the attachment.
     *
     * @param processedRecords List of InputRecord objects containing test results.
     * @param emails           Comma-separated string of email addresses to send the report to.
     */
    @Async
    public void generateAndSendReport(List<InputRecord> processedRecords, String emails) {
        try (Workbook resultWorkbook = new XSSFWorkbook()) {
            Sheet resultSheet = resultWorkbook.createSheet("Test Results");
            Row headerRow = resultSheet.createRow(0);
            String[] headers = {"API Type", "Method", "API URL", "Payload", "Headers", "Params", "Response Code", "Response Time", "Response Message"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            AtomicInteger rowIndex = new AtomicInteger(1); // Start from row 1 (after header)
            processedRecords.parallelStream().forEach(record -> {
                Row row = resultSheet.createRow(rowIndex.getAndIncrement());
                row.createCell(0).setCellValue(record.getApitype());
                row.createCell(1).setCellValue(record.getMethod());
                row.createCell(2).setCellValue(record.getApiurl());
                row.createCell(3).setCellValue(record.getPayload());
                row.createCell(4).setCellValue(record.getHeaders() != null ? record.getHeaders().toString() : "");
                row.createCell(5).setCellValue(record.getParams() != null ? record.getParams().toString() : "");
                row.createCell(6).setCellValue(record.getResponseCode());
                row.createCell(7).setCellValue(record.getResponseTime());
                row.createCell(8).setCellValue(record.getResponseMessage());
            });

                // Send email with the Excel file
                List<String> emailList = List.of(emails.split("\\s*,\\s*"));
                logger.info("Sending report to: {}", emailList);
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    resultWorkbook.write(outputStream);
                    byte[] excelData = outputStream.toByteArray();
                    sendEmailWithAttachment(emailList, excelData, "Test Results.xlsx");
                }
        } catch (Exception exception) {
            logger.error("Error generating or sending Excel file: ", exception.getMessage());
        }
    }
    /**
     * This method sends an email with an attachment to a list of email addresses.
     * It uses JMS to queue the email message for processing by a listener.
     *
     * @param emailList       List of email addresses to send the report to.
     * @param attachmentData  Byte array containing the Excel file data.
     * @param fileName        Name of the file to be attached in the email.
     */
    public void sendEmailWithAttachment(List<String> emailList, byte[] attachmentData, String fileName) {
        emailList.parallelStream().forEach(email -> {
            logger.info("Queuing email to: {}", email);
            EmailMessage emailMessage = new EmailMessage(email, attachmentData, fileName);
            logger.info("Email message created: {}", emailMessage.toString());
            emailSender.processEmail(emailMessage);
        });
    }
}
