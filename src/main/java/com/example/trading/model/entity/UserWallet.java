package com.example.trading.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_wallet")
@Getter
@Setter
public class UserWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private double balance;

    @Column(name = "btc_balance")
    private double btcBalance;

    @Column(name = "eth_balance")
    private double ethBalance;
}
