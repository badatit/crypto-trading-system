package com.example.trading.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeRequest {
    private Long userId;
    private String orderType;
    private String symbol;
    private Double amount;
}
