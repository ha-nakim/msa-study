package com.eazybytes.accounts.listener;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;

@Component
public class CircuitBreakerEventListener {
  private final StreamBridge streamBridge;

    public CircuitBreakerEventListener(StreamBridge streamBridge, CircuitBreakerRegistry registry) {
        this.streamBridge = streamBridge;

        // 모든 CircuitBreaker의 상태 변화 이벤트 리스너 등록
        registry.getAllCircuitBreakers()
                .forEach(cb -> cb.getEventPublisher().onStateTransition(this::handleStateTransition));
    }

    @EventListener
    public void handleStateTransition(CircuitBreakerOnStateTransitionEvent event) {
        String message = String.format("CircuitBreaker '%s' changed from %s to %s",
                event.getCircuitBreakerName(), event.getStateTransition().getFromState(), event.getStateTransition().getToState());

        System.out.println("-------------->>>>>>>>>>>" + message);

        // 메시지를 Kafka 등으로 전송
        streamBridge.send("circuitBreakerTopic-out-0", message);
    }
}
