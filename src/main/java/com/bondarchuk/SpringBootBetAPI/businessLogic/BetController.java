package com.bondarchuk.SpringBootBetAPI.businessLogic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/bets")
public class BetController {
    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping
    public ResponseEntity<String> placeBet(@RequestBody BetRequest request) {
        try {
            betService.placeBet(request.getUserName(), request.getCar(), request.getAmount());
            return ResponseEntity.ok("Bet placed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<BetResponse> getBets(@RequestParam(required = false) Car car) {
        Map<String, BigDecimal> bets = betService.getBets(car);
        return ResponseEntity.ok(new BetResponse(bets));
    }
}
