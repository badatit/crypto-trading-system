package com.example.trading.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWalletResponse {
    private Long userId;
    private double balance;
    private double btcBalance;
    private double ethBalance;
}
