package com.example.trading.service;

import com.example.trading.model.response.UserWalletResponse;

public interface WalletService {
    UserWalletResponse getWalletByUserId(Long userId);
}
