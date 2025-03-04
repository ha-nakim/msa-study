package com.eazybytes.accounts.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class ExternalService {

  @CircuitBreaker(name="hnServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
  public String callExternalService() {
      // 랜덤으로 실패 발생 if (random.nextBoolean()) {}
      
      throw new RuntimeException("Service failed!");
  }

  public String fallbackMethod(Throwable t) {
      return "Fallback response due to: " + t.getMessage();
  }
}
