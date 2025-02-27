package com.mobigen.message.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

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

    public Consumer<Message<String>> circuitBreakerTopic() {
      return message -> {
        System.out.println("Received CircuitBreaker Event: " + message.getPayload());
    };
    }

}
