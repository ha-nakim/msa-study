package com.eazybytes.gatewayserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping
    public Mono<Map<String, String>> fallback(ServerWebExchange exchange, @RequestParam(value = "routeId", required = false) String routeId) {
        // return Mono.just("An error occurred. Please try after some time or contact support team!!!");
        Map<String, String> response = new HashMap<>();
        
        // 장애 발생한 서비스 경로 가져오기
        String failedService = exchange.getRequest().getURI().toString();
        
        response.put("message", "서비스가 현재 사용 불가능합니다. 잠시 후 다시 시도해주세요.");
        response.put("failedService", failedService);
        
        // routeId가 전달되면 추가 정보 제공
        if (routeId != null) {
            response.put("routeId", routeId);
        }

        return Mono.just(response);
    }

}
