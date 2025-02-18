package com.bondarchuk.SpringBootBetAPI.businessLogic;

import java.math.BigDecimal;

public class BetRequest {
    private String userName;
    private Car car;
    private BigDecimal amount;

    public BetRequest(String userName, Car car, BigDecimal amount) {
        this.userName = userName;
        this.car = car;
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public Car getCar() {
        return car;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
