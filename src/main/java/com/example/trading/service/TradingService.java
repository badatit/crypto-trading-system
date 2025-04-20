package com.example.trading.service;

import com.example.trading.model.request.TradeRequest;
import com.example.trading.model.response.TradeResponse;
import com.example.trading.model.response.TradingHistoryResponse;

import java.util.List;

public interface TradingService {
    TradeResponse handleTradeCryptoOrder(TradeRequest tradeRequestDto);
    List<TradingHistoryResponse> getUserTradingHistory(Long userId);
}
