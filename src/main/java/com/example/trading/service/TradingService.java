package com.example.trading.service;

import com.example.trading.model.request.TradeRequest;
import com.example.trading.model.response.TradeOrderResponse;
import com.example.trading.model.response.TradingHistoryResponse;

import java.util.List;

public interface TradingService {
    TradeOrderResponse handleTradeCryptoOrder(TradeRequest tradeRequestDto);
    List<TradingHistoryResponse> getUserTradingHistory(Long userId);
}
