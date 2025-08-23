package com.qbtechlabs.altlens.controller;

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
    @GetMapping(ENDPOINT_AI_DASHBOARD)
    public String showDashboard() {
        return KEY_AI_DASHBOARD;
    }

    @PostMapping(ENDPOINT_AI_RUN_TESTS)
    public String runTests(@RequestBody String inputURI, Model model) {
        logger.info("Triggering AI Test with URI: [{}]", inputURI);
        // Add dummy data to the model
        model.addAttribute("processedRecords", new ArrayList<>());
        logger.info("AI Test run completed ");
        return "airesults"; // Ensure this matches the JSP file name without the `.jsp` extension
    }
}
