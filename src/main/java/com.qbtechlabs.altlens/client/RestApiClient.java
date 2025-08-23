package com.qbtechlabs.altlens.client;

import com.qbtechlabs.altlens.model.InputRecord;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class RestApiClient {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestApiClient.class);
    /**
     * This method is a placeholder for executing tests via the REST API.
     * It can be extended to include actual logic for test execution.
     */
    public InputRecord decideCallAndCollectResponse(String apiType,String method, String apiurl, String payload, Map<String, Object> headers, Map<String, Object> params) {
        Map<String, Object> responseDetails = executeApiCall(method, apiurl, payload, headers, params);
        return new InputRecord(apiType, method, apiurl, payload, headers, params,
                responseDetails.get("responseCode").toString(),
                responseDetails.get("responseTime").toString(),
                responseDetails.get("responseMessage")!=null ? responseDetails.get("responseMessage").toString() : "NA"
        );
    }

    private Map<String, Object> executeApiCall(String method, String apiurl, String payload, Map<String, Object> headers, Map<String, Object> params) {
        Map<String, Object> responseDetails = new HashMap<>();
        WebClient webClient = WebClient.builder().baseUrl(apiurl).build();

        try {
            // Build the request
            WebClient.RequestBodySpec requestSpec = webClient.method(resolveHttpMethod(method))
                    .uri(uriBuilder -> {
                        if (params != null) {
                            params.forEach(uriBuilder::queryParam);
                        }
                        return uriBuilder.build();
                    })
                    .headers(httpHeaders -> {
                        if (headers != null) {
                            headers.forEach((key, value) -> httpHeaders.set(key, value.toString()));
                        }
                    });

            // Send the request and retrieve the response
            ClientResponse clientResponse;
            long startTime = System.currentTimeMillis();
            if (payload != null && !payload.isEmpty()) {
                clientResponse = requestSpec.bodyValue(payload).exchangeToMono(Mono::just).block();
            } else {
                clientResponse = requestSpec.exchangeToMono(Mono::just).block();
            }
            long responseTime = System.currentTimeMillis() - startTime;
            if (clientResponse != null) {
                responseDetails.put("responseMessage", clientResponse.bodyToMono(String.class).block());
                responseDetails.put("responseCode", clientResponse.statusCode().value());
                responseDetails.put("responseTime", responseTime);
            }
        } catch (Exception exception) {
            responseDetails.put("responseMessage", exception.getMessage());
            responseDetails.put("responseCode", 500);
            logger.error("Error during API call: ", exception);
        }
        return responseDetails;
    }

    private HttpMethod resolveHttpMethod(String method) {
        try {
            return HttpMethod.valueOf(method.toUpperCase());
        } catch (IllegalArgumentException exception) {
            logger.warn("Invalid HTTP method: {}. Defaulting to GET.", method);
            return HttpMethod.GET;
        }
    }
}
