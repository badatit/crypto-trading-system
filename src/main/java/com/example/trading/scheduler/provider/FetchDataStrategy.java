package com.example.trading.scheduler.provider;

import com.example.trading.model.dto.AggregatedPriceDTO;

import java.util.List;

public interface FetchDataStrategy {
    AggregatedPriceDTO fetchPrice(String symbol);
}
