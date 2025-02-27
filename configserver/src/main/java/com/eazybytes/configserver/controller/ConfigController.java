package com.eazybytes.configserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.configserver.publisher.ConfigChangePublisher;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ConfigController {
  
  ConfigChangePublisher publisher;

  @PostMapping("/sendRefresh")
    public ResponseEntity<String> sendMessage() {
      publisher.publicConfigChange();
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("Configuration refresh event sent!");

    }
}
