package com.qbtechlabs.altlens.model;

import java.util.Map;

public class InputRecord {
    private String apitype;
    private String method;
    private String apiurl;
    private String payload;
    private Map<String, Object> headers;
    private Map<String, Object> params;
    private String responseCode;
    private String responseTime;
    private String responseMessage;


    public InputRecord(String apitype, String method, String apiurl, String payload, Map<String, Object> headers, Map<String, Object> params, String responseCode, String responseTime, String responseMessage) {
        this.apitype = apitype;
        this.method = method;
        this.apiurl = apiurl;
        this.payload = payload;
        this.headers = headers;
        this.params = params;
        this.responseCode = responseCode;
        this.responseTime = responseTime;
        this.responseMessage = responseMessage;
    }

    public String getApitype() {
        return apitype;
    }

    public void setApitype(String apitype) {
        this.apitype = apitype;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public String toString() {
        return "Record{" +
                "apitype='" + apitype + '\'' +
                ", method='" + method + '\'' +
                ", apiurl='" + apiurl + '\'' +
                ", payload='" + payload + '\'' +
                ", headers='" + headers + '\'' +
                ", params='" + params + '\'' +
                ", responseCode=" + responseCode +
                ", responseTime=" + responseTime +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}