package com.eazybytes.configserver.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ConfigChangePublisher {
  
  private ApplicationEventPublisher eventPublisher;

  public void publicConfigChange() {
   // entPublisher.publishEvent(new RefreshRemoteApplicationEvent(this, "config-server", null));
  }
}
