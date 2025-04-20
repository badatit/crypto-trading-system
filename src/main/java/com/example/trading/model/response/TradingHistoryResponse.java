package com.example.trading.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TradingHistoryResponse {
    private Long userId;
    private String symbol;
    private String orderType;
    private double price;
    private double amount;
    private String status;
    private LocalDateTime createdAt;
}
