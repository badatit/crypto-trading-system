package com.example.trading.repository;

import com.example.trading.model.entity.CryptoPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {

    @Query("SELECT c FROM CryptoPrice c WHERE c.symbol in (:symbols) ORDER BY c.createdAt DESC")
    List<CryptoPrice> findLatestPrices(List<String> symbols, Pageable Pageable);

    Optional<CryptoPrice> findTopBySymbolOrderByCreatedAtDesc(String symbol);
}
