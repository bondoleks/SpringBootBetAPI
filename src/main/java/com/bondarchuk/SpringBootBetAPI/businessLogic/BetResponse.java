package com.bondarchuk.SpringBootBetAPI.businessLogic;

import java.math.BigDecimal;
import java.util.Map;

public class BetResponse {
    private final Map<String, BigDecimal> bets;

    public BetResponse(Map<String, BigDecimal> bets) {
        this.bets = bets;
    }

    public Map<String, BigDecimal> getBets() {
        return bets;
    }
}

