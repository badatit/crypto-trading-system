package com.example.trading.service;

import com.example.trading.model.response.CryptoBestAggregatePriceResponse;

import java.util.List;

public interface CryptoAggregationPriceService {
    List<CryptoBestAggregatePriceResponse> findLatestBestAggregatedPrices();
}
