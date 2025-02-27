package com.eazybytes.gatewayserver;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	private static final Logger logger = LoggerFactory.getLogger(GatewayserverApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

  @Bean
	public RouteLocator mgbankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		logger.debug("Configuring routes for TEAM7");
		return routeLocatorBuilder.routes()
            .route("accounts", p -> p
                .path("/team7/accounts/**")
                .filters( f -> f.rewritePath("/team7/accounts/(?<segment>.*)","/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                      .setFallbackUri("forward:/fallback?routeId=accounts")
                    )
                )
                .uri("lb://ACCOUNTS"))
            .route("loans", p -> p
                .path("/team7/loans/**")
                .filters( f -> f.rewritePath("/team7/loans/(?<segment>.*)","/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .circuitBreaker(config -> config.setName("loansCircuitBreaker")
                      .setFallbackUri("forward:/fallback?routeId=loans")
                    )
                )
                .uri("lb://LOANS"))
            .route("cards", p -> p
                .path("/team7/cards/**")
                .filters( f -> f.rewritePath("/team7/cards/(?<segment>.*)","/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .circuitBreaker(config -> config.setName("cardsCircuitBreaker")
                      .setFallbackUri("forward:/fallback?routeId=cards")
                    )
                )
                .uri("lb://CARDS")).build();
	}
}
