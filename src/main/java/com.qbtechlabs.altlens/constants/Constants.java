package com.qbtechlabs.altlens.constants;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constants {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /******************************Endpoints**********************************/
    public static final String ENDPOINT_DASHBOARD = "/dashboard";
    public static final String ENDPOINT_RUN_TESTS = "/run-tests";
    public static final String ENDPOINT_SAMPLE_INPUT = "/sampleInput";
    public static final String ENDPOINT_AI_DASHBOARD = "/ai-dashboard";
    public static final String ENDPOINT_AI_RUN_TESTS = "/ai-test";
    /******************************KEYS**********************************/
    public static final String KEY_SAMPLE_INPUT = "sampleInput";
    public static final String KEY_FILE_UPLOAD = "fileUpload";
    public static final String KEY_DASHBOARD = "dashboard";
    public static final String KEY_EMAILS = "email";
    public static final String KEY_AI_DASHBOARD = "aidashboard";
    /******************************Meassages**********************************/
    public static final String MSG_CONFIGURING_TOMCAT_VIRTUAL_THREADS = "****** Configuring Tomcat to use virtual threads for better concurrency handling ******";
    public static final String MSG_TRIGGERRING_TEST = "Triggering tests, it may take sometime, please wait...";
}
