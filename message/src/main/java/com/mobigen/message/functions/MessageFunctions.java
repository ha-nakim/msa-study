package com.mobigen.message.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.mobigen.message.dto.CircuitBreakerDto;
import com.mobigen.message.dto.ErrorMessageDto;
import com.mobigen.message.dto.MessageDto;

import org.springframework.messaging.Message;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@AllArgsConstructor
public class MessageFunctions {

    @Bean
    public Function<MessageDto, String> message() {
      return messageDto -> {
        System.out.println("Message Sent: " + messageDto.toString());
        return messageDto.service() + " : " + messageDto.message();
      };
    }

    @Bean
    public Function<ErrorMessageDto, String> email() {
      return mamessageDto -> {
        System.out.println("Message Sent: " + mamessageDto.toString());
        return mamessageDto.service() + " : " + mamessageDto.message();
      };
    }

    @Bean
    public Function<CircuitBreakerDto, String> circuitBreakerTopic() {

      // CircuitBreaker 상태 변경 : event={circuitBreakerName=hnServiceCircuitBreaker, stateTransition=HALF_OPEN_TO_OPEN, creationTime=2025-03-04T17:51:25.9280277+09:00, eventType=STATE_TRANSITION}]
      // 요청 거부됨 : event={circuitBreakerName=hnServiceCircuitBreaker, creationTime=2025-03-04T17:51:26.5919885+09:00, eventType=NOT_PERMITTED}]
      return mamessageDto -> {
        System.out.println("Message Sent: " + mamessageDto.toString());
        return mamessageDto.serviceId() + " : " + mamessageDto.message() + ":" + mamessageDto.event();
      };
    }

}
