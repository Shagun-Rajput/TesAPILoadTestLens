package com.qbtechlabs.altlens.controller;

import com.qbtechlabs.altlens.service.AITestRunnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.qbtechlabs.altlens.constants.Constants.*;

@Controller
public class AITestRunController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AITestRunController.class);
    private final AITestRunnerService aiTestRunnerService;
    public AITestRunController(AITestRunnerService aiTestRunnerService) {
        this.aiTestRunnerService = aiTestRunnerService;
    }
    /**
     * This controller handles the AI test execution requests.
     * It provides endpoints for displaying the AI dashboard and running AI tests.
     * The methods are designed to interact with the AITestRunnerService for executing AI tests and fetching results.
     */
    @GetMapping(ENDPOINT_AI_DASHBOARD)
    public String showDashboard() {
        return KEY_AI_DASHBOARD;
    }

    @PostMapping(ENDPOINT_AI_RUN_TESTS)
    public String runTests(@RequestBody String inputURI, Model model) {
        logger.info("Controller: Triggering AI Test with URI: [{}]", inputURI);
        return aiTestRunnerService.runAITests(inputURI, model);
    }
}
