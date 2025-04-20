package com.example.trading.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TradeResponse {
    private String status;
    private String message;
    private Long transactionId;

}
