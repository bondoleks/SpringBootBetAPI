package com.bondarchuk.SpringBootBetAPI.businessLogic;

import java.math.BigDecimal;

public class Bet {
    private final User user;
    private final Car car;
    private final BigDecimal amount;

    public Bet(User user, Car car, BigDecimal amount) {
        this.user = user;
        this.car = car;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
