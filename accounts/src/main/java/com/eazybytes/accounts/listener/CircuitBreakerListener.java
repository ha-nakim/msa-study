package com.eazybytes.accounts.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import io.github.resilience4j.circuitbreaker.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import com.eazybytes.accounts.dto.CircuitBreakerDto;

@Component
@AllArgsConstructor
public class CircuitBreakerListener {

    private CircuitBreakerRegistry circuitBreakerRegistry;

    private final StreamBridge streamBridge;

    @PostConstruct
    public void registerListeners() {
        // application.yml에서 설정한 Circuit Breaker 가져오기
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("hnServiceCircuitBreaker");

        // 리스너 등록
        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> streamBridge.send("circuitBreakerTopic-out-0", new CircuitBreakerDto("account", "CircuitBreaker 상태 변경", event))              )
                .onFailureRateExceeded(event -> streamBridge.send("circuitBreakerTopic-out-0", new CircuitBreakerDto("account", "실패율 초과", event)))
                .onCallNotPermitted(event -> streamBridge.send("circuitBreakerTopic-out-0", new CircuitBreakerDto("account", "요청 거부됨", event)))
                .onError(event -> streamBridge.send("circuitBreakerTopic-out-0", new CircuitBreakerDto("account", "에러 발생", event)));
                // .onSuccess(event -> streamBridge.send("circuitBreakerTopic-out-0", new CircuitBreakerDto("account", "성공 요청", event)));

        System.out.println("Circuit Breaker 리스너 등록 완료!");
    }
}
