package com.meridian.backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AlphaVantageClient {

    private final RestClient restClient;

    @Value("${ALPHA_VANTAGE_API_KEY}")
    private String apiKey;

    public AlphaVantageClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://www.alphavantage.co").build();
    }

    public GlobalQuoteResponse fetchQuote(String symbol) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/query")
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .body(GlobalQuoteResponse.class);
    }
}