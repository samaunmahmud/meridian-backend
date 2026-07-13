package com.meridian.backend.controller;

import com.meridian.backend.client.AlphaVantageClient;
import com.meridian.backend.client.GlobalQuoteResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketDataTestController {

    private final AlphaVantageClient alphaVantageClient;

    public MarketDataTestController(AlphaVantageClient alphaVantageClient) {
        this.alphaVantageClient = alphaVantageClient;
    }

    @GetMapping("/api/test-quote/{symbol}")
    public GlobalQuoteResponse testQuote(@PathVariable String symbol) {
        return alphaVantageClient.fetchQuote(symbol);
    }
}