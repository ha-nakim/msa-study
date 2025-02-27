package com.mobigen.message.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mobigen.message.dto.MessageDto;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    @Bean
    public Function<MessageDto, String> message() {
      return messageDto -> {
        System.out.println("Message Sent: " + messageDto.toString());
        return messageDto.service() + " : " + messageDto.message();
      };
    }

}
