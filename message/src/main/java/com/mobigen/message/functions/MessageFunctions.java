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

  private final JavaMailSender mailSender;

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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("admin@example.com");
        message.setSubject("[경고] 서비스 장애 발생: " + mamessageDto.service());
        message.setText("서비스: " + mamessageDto.service() + "\n" +
                        "URL: " + mamessageDto.url() + "\n" +
                        "에러: " + mamessageDto.message() + "\n\n" +
                        "긴급 점검이 필요합니다.");

        mailSender.send(message);
        System.out.println("?? 장애 경고 이메일 전송 완료!");
        
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
