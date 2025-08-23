package com.qbtechlabs.altlens.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface AITestRunnerService {

    /**
     * This service is responsible for executing AI tests and fetching results.
     * It can be extended to include methods for running specific AI tests, retrieving results, etc.
     * Currently, it serves as a placeholder for future AI test execution logic.
     *
     * @param inputURI the URI to process
     * @return a string indicating the result of the AI test execution
     */
    String runAITests(String inputURI, Model model);
}
