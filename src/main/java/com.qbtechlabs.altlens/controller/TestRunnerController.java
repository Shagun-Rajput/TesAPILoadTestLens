package com.qbtechlabs.altlens.controller;


import com.qbtechlabs.altlens.service.TestRunnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.qbtechlabs.altlens.constants.Constants.*;


@Controller
public class TestRunnerController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestRunnerController.class);
    private final TestRunnerService testRunnerService;
    public TestRunnerController(TestRunnerService testRunnerService) {

        this.testRunnerService = testRunnerService;
    }
    /**
     * This controller handles the main dashboard and test execution requests.
     * It provides endpoints for displaying the dashboard, running tests, and showing sample input.
     * The methods are designed to interact with the TestRunnerService for executing tests and fetching results.
     */
    @GetMapping(ENDPOINT_DASHBOARD)
    public String showDashboard() {
        return KEY_DASHBOARD;
    }

    @PostMapping(ENDPOINT_RUN_TESTS)
    public String runTests(@RequestParam(KEY_FILE_UPLOAD) MultipartFile file, Model model) {
        logger.info(MSG_TRIGGERRING_TEST);
        return testRunnerService.executeAndFetchResults(file, model);
    }
    @GetMapping(ENDPOINT_SAMPLE_INPUT)
    public String sampleInput() {
        return KEY_SAMPLE_INPUT;
    }

}
