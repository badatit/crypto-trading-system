package com.example.trading.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CryptoBestAggregatePriceResponse {
    private String symbol;
    private double bestBid;
    private double bestAsk;
    private String sourceBid;
    private String sourceAsk;
    private LocalDateTime createdAt;
}
