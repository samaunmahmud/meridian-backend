package com.meridian.backend.controller;

import com.meridian.backend.dto.PricePointResponse;
import com.meridian.backend.dto.TickerResponse;
import com.meridian.backend.service.MarketDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/tickers")
    public List<TickerResponse> getTickers() {
        return marketDataService.getAllTickers();
    }

    @GetMapping("/prices/{symbol}")
    public List<PricePointResponse> getPrices(@PathVariable String symbol) {
        return marketDataService.getPriceHistory(symbol);
    }
}