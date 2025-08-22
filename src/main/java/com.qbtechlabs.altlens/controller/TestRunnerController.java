package com.qbtechlabs.altlens.controller;


import com.qbtechlabs.altlens.service.TestRunnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TestRunnerController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestRunnerController.class);
    private final TestRunnerService testRunnerService;
    public TestRunnerController(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;
    }
    /*
     * This controller is responsible for handling requests related to test execution.
     * It uses the TestRunnerService to perform the actual test execution logic.
     * The controller can be extended with endpoints to trigger tests, retrieve results, etc.
     * Currently, it serves as a placeholder for future test-related functionalities.
     */
    @GetMapping("/")
    public String showDashboard() {
        return "dashboard"; // This matches the name of the JSP file (dashboard.jsp)
    }
    @PostMapping("/run-tests")
    public String runTests() {
        logger.info("Running tests...");
        // Here you would call the testRunnerService to execute tests
        // For now, we return a simple message
        return "Tests executed successfully!";
    }

}
