package com.mobigen.message.dto;

public record CircuitBreakerDto (
  String serviceId, String message, Object event
){}