package com.eazybytes.gatewayserver.dto;

public record ErrorMessageDto (
  String service, String message, String url
){}
