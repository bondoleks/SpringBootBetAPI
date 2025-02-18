package com.bondarchuk.SpringBootBetAPI;

import com.bondarchuk.SpringBootBetAPI.businessLogic.BetService;
import com.bondarchuk.SpringBootBetAPI.businessLogic.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BetServiceTest {
    private BetService betService;

    @BeforeEach
    void setUp() {
        betService = new BetService();
    }

    @Test
    void placeBet_ShouldAddSingleBet() {
        betService.placeBet("Alex", Car.FERRARI, new BigDecimal("100.50"));
        Map<String, BigDecimal> bets = betService.getBets(null);

        assertEquals(new BigDecimal("100.50"), bets.get(Car.FERRARI.getName()));
    }

    @Test
    void placeBet_ShouldAccumulateBetsForSameCar() {
        betService.placeBet("Alex", Car.BMW, new BigDecimal("50.00"));
        betService.placeBet("John", Car.BMW, new BigDecimal("30.00"));

        Map<String, BigDecimal> bets = betService.getBets(null);

        assertEquals(new BigDecimal("80.00"), bets.get(Car.BMW.getName()));
    }

    @Test
    void placeBet_ShouldWorkForDifferentUsersAndCars() {
        betService.placeBet("Alex", Car.FERRARI, new BigDecimal("100.00"));
        betService.placeBet("John", Car.AUDI, new BigDecimal("200.00"));

        Map<String, BigDecimal> bets = betService.getBets(null);

        assertEquals(new BigDecimal("100.00"), bets.get(Car.FERRARI.getName()));
        assertEquals(new BigDecimal("200.00"), bets.get(Car.AUDI.getName()));
    }

    @Test
    void getBets_ShouldReturnBetsForSpecificCar() {
        betService.placeBet("Alex", Car.HONDA, new BigDecimal("75.00"));
        betService.placeBet("John", Car.HONDA, new BigDecimal("25.00"));

        Map<String, BigDecimal> bets = betService.getBets(Car.HONDA);

        assertEquals(1, bets.size());
        assertEquals(new BigDecimal("100.00"), bets.get(Car.HONDA.getName()));
    }

    @Test
    void getBets_ShouldReturnEmptyMapIfNoBetsForCar() {
        Map<String, BigDecimal> bets = betService.getBets(Car.FERRARI);
        assertEquals(new BigDecimal("0"), bets.get(Car.FERRARI.getName()));
    }

    @Test
    void getBets_ShouldReturnAllCarsWithZeroBetsIfNoSpecificBet() {
        Map<String, BigDecimal> bets = betService.getBets(null);

        for (Car car : Car.values()) {
            assertEquals(new BigDecimal("0"), bets.get(car.getName()));
        }
    }

    @Test
    void placeBet_ShouldThrowExceptionForNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                betService.placeBet("Alex", Car.BMW, new BigDecimal("-50")));

        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    void placeBet_ShouldThrowExceptionForZeroAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                betService.placeBet("Alex", Car.AUDI, BigDecimal.ZERO));

        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    void placeBet_ShouldHandleHighVolumeOfBets() {
        for (int i = 0; i < 1000; i++) {
            betService.placeBet("User" + i, Car.HONDA, new BigDecimal("10.00"));
        }

        Map<String, BigDecimal> bets = betService.getBets(null);
        assertEquals(new BigDecimal("10000.00"), bets.get(Car.HONDA.getName()));
    }
}