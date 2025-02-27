package com.eazybytes.gatewayserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.eazybytes.gatewayserver.dto.ErrorMessageDto;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path="/fallback")
@AllArgsConstructor
public class FallbackController {

  private final StreamBridge streamBridge;

  @GetMapping
  public Mono<Map<String, String>> fallback(@RequestHeader HttpHeaders headers) {
      // return Mono.just("An error occurred. Please try after some time or contact support team!!!");
      Map<String, String> response = new HashMap<>();

      // 장애 발생한 서비스 경로 가져오기
      String originalUrl = headers.getFirst("X-Original-URL");
      String serviceId = headers.getFirst("X-Service-Id");
      
      response.put("message", "서비스가 현재 사용 불가능합니다. 잠시 후 다시 시도해주세요.");
      response.put("failedService", originalUrl);
      response.put("routeId", serviceId);

      // 장애 발생 이벤트를 RabbitMQ로 전송
      ErrorMessageDto dto = new ErrorMessageDto(serviceId, "서비스 통신 불가!!", originalUrl);

      streamBridge.send("sendCommunication-out-0", dto);

      return Mono.just(response);
  }

}
