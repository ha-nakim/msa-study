package com.eazybytes.accounts.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ExternalService {
  private final Random random = new Random();

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    public String callExternalService() {
        // 랜덤으로 실패 발생
        if (random.nextBoolean()) {
            throw new RuntimeException("Service failed!");
        }
        return "Success!";
    }

    public String fallbackMethod(Throwable t) {
        return "Fallback response due to: " + t.getMessage();
    }
}
