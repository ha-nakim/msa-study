package com.mobigen.message.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mobigen.message.dto.MessageDto;

import java.util.Map;
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

    @Bean
    public Function<Map<String, String>, String> mail() {
      return map -> {
        System.out.println("Message Sent: " + map.get("error") + "(" + map.get("url") + ")");
        return map.get("service") + " : " + map.get("error") + "(" + map.get("url") + ")";
      };
    }

}
