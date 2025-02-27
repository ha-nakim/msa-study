package com.mobigen.message.dto;

public record ErrorMessageDto (
  String service, String message, String url
){}
