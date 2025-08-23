package com.qbtechlabs.altlens.model;

import java.io.Serializable;

public class EmailMessage implements Serializable {
    private String email;
    private byte[] attachmentData;
    private String fileName;

    public EmailMessage() {
    }

    public EmailMessage(String email, byte[] attachmentData, String fileName) {
        this.email = email;
        this.attachmentData = attachmentData;
        this.fileName = fileName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAttachmentData() {
        return attachmentData;
    }

    public void setAttachmentData(byte[] attachmentData) {
        this.attachmentData = attachmentData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "email='" + email + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}