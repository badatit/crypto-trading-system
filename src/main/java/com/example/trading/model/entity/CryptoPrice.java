package com.example.trading.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_price")
@Getter
@Setter
@ToString
public class CryptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    @Column(name = "best_bid")
    private double bestBid;

    @Column(name = "best_ask")
    private double bestAsk;

    @Column(name = "source_bid")
    private String sourceBid;

    @Column(name = "source_ask")
    private String sourceAsk;

    @Column(name = "best_bid_qty")
    private double bestBidQty;

    @Column(name = "best_ask_qty")
    private double bestAskQty;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
