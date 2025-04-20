package com.example.trading.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TradeOrderResponse {
    private String status;
    private String message;
    private Long transactionId;

}
