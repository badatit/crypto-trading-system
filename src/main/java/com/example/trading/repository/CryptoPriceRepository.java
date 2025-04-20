package com.example.trading.repository;

import com.example.trading.model.entity.CryptoAggregationPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CryptoPriceRepository extends JpaRepository<CryptoAggregationPrice, Long> {

    @Query("SELECT c FROM CryptoAggregationPrice c WHERE c.symbol in (:symbols) ORDER BY c.createdAt DESC")
    List<CryptoAggregationPrice> findLatestPrices(List<String> symbols, Pageable Pageable);

    Optional<CryptoAggregationPrice> findTopBySymbolOrderByCreatedAtDesc(String symbol);
}
