package com.qbtechlabs.altlens.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface TestRunnerService {
    String executeAndFetchResults(MultipartFile file, Model model, String emails);
    /**
     * This service is responsible for executing tests by interacting with the RestApiClient.
     * It can be extended to include methods for running specific tests, retrieving results, etc.
     * Currently, it serves as a placeholder for future test execution logic.
     */
    
}
