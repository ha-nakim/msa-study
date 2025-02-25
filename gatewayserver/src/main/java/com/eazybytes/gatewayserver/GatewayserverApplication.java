package com.eazybytes.gatewayserver;

import java.time.Duration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

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
            .route(p -> p
                .path("/team7/accounts/**")
                .filters( f -> f.rewritePath("/team7/accounts/(?<segment>.*)","/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                    // .setFallbackUri("forward:/contactSupport")
                    )
                )
                .uri("lb://ACCOUNTS"))
            .route(p -> p
                .path("/team7/loans/**")
                .filters( f -> f.rewritePath("/team7/loans/(?<segment>.*)","/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						    .retry(retryConfig -> retryConfig.setRetries(3)
                    	.setMethods(HttpMethod.GET)
                    	.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true))
                )
                .uri("lb://LOANS"))
            .route(p -> p
                .path("/team7/cards/**")
                .filters( f -> f.rewritePath("/team7/cards/(?<segment>.*)","/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                )
                .uri("lb://CARDS")).build();
	}
}
