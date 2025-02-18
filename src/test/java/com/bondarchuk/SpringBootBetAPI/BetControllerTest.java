package com.bondarchuk.SpringBootBetAPI;

import com.bondarchuk.SpringBootBetAPI.businessLogic.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BetControllerTest {
    @Mock
    private BetService betService;

    @InjectMocks
    private BetController betController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void placeBet_ShouldReturnSuccessMessage() {
        BetRequest request = new BetRequest("Alex", Car.BMW, new BigDecimal("50.75"));

        ResponseEntity<String> response = betController.placeBet(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Bet placed successfully", response.getBody());
        verify(betService, times(1)).placeBet("Alex", Car.BMW, new BigDecimal("50.75"));
    }

    @Test
    void placeBet_ShouldReturnBadRequestForNegativeAmount() {
        BetRequest request = new BetRequest("Alex", Car.BMW, new BigDecimal("-20.00"));

        doThrow(new IllegalArgumentException("Amount must be positive"))
                .when(betService).placeBet(anyString(), any(Car.class), any(BigDecimal.class));

        ResponseEntity<String> response = betController.placeBet(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Amount must be positive", response.getBody());
    }

    @Test
    void getBets_ShouldReturnBetsForSpecificCar() {
        when(betService.getBets(Car.AUDI)).thenReturn(Map.of(Car.AUDI.getName(), new BigDecimal("150.25")));

        ResponseEntity<BetResponse> response = betController.getBets(Car.AUDI);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(new BigDecimal("150.25"), response.getBody().getBets().get(Car.AUDI.getName()));
    }

    @Test
    void getBets_ShouldReturnAllBetsWhenNoCarSpecified() {
        when(betService.getBets(null)).thenReturn(Map.of(
                Car.BMW.getName(), new BigDecimal("300.00"),
                Car.HONDA.getName(), new BigDecimal("50.00")
        ));

        ResponseEntity<BetResponse> response = betController.getBets(null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(new BigDecimal("300.00"), response.getBody().getBets().get(Car.BMW.getName()));
        assertEquals(new BigDecimal("50.00"), response.getBody().getBets().get(Car.HONDA.getName()));
    }

    @Test
    void getBets_ShouldReturnEmptyResponseWhenNoBets() {
        when(betService.getBets(null)).thenReturn(Map.of());

        ResponseEntity<BetResponse> response = betController.getBets(null);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getBets().isEmpty());
    }

    @Test
    void getBets_ShouldReturnZeroBetsForCarWithNoBets() {
        when(betService.getBets(Car.FERRARI)).thenReturn(Map.of(Car.FERRARI.getName(), new BigDecimal("0.00")));

        ResponseEntity<BetResponse> response = betController.getBets(Car.FERRARI);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(new BigDecimal("0.00"), response.getBody().getBets().get(Car.FERRARI.getName()));
    }
}