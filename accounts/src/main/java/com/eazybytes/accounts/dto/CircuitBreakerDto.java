package com.eazybytes.accounts.dto;

public record CircuitBreakerDto (
  String serviceId, String message, Object event
){}