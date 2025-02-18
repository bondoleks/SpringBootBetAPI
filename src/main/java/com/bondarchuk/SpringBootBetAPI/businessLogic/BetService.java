package com.bondarchuk.SpringBootBetAPI.businessLogic;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class BetService {
    private final List<Bet> bets = new CopyOnWriteArrayList<>();

    public void placeBet(String userName, Car car, BigDecimal amount) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name must not be empty");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (car == null || !EnumSet.allOf(Car.class).contains(car)) {
            throw new IllegalArgumentException("Invalid car selection");
        }

        User user = new User(userName);
        bets.add(new Bet(user, car, amount));
    }

    public Map<String, BigDecimal> getBets(Car car) {
        // Формуємо мапу з усіма авто, де сума ставок за замовчуванням 0
        Map<String, BigDecimal> result = Arrays.stream(Car.values())
                .collect(Collectors.toMap(Car::getName, c -> BigDecimal.ZERO));

        // Додаємо реальні ставки з колекції `bets`
        bets.stream()
                .collect(Collectors.groupingBy(
                        bet -> bet.getCar().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Bet::getAmount, BigDecimal::add)
                ))
                .forEach(result::put); // Оновлюємо ставки в основній мапі

        // Якщо передане авто є в списку, повертаємо тільки його ставку
        if (car != null && result.containsKey(car.getName()) && result.get(car.getName()).compareTo(BigDecimal.ZERO) > 0) {
            return Map.of(car.getName(), result.get(car.getName()));
        }

        return result; // Повертаємо всі авто з їхніми ставками
    }
}

