package com.gigglegazette.api_gateway.config;

import com.gigglegazette.api_gateway.security.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Article Service
                .route("article-service-articles", r -> r.path("/articles/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://article-service"))
                .route("article-service-categories", r -> r.path("/categories/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://article-service"))
                .route("article-service-comments", r -> r.path("/comments/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://article-service"))
                .route("article-service-images", r -> r.path("/images/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://article-service"))
                .route("article-service-subcategory", r -> r.path("/subcategories/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://article-service"))

                // User Service
                .route("user-service-users", r -> r.path("/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route("user-service-roles", r -> r.path("/roles/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route("user-service-profiles", r -> r.path("/profiles/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route("user-service-permissions", r -> r.path("/permissions/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))

                // Auth Service (No filter)
                .route("auth-service", r -> r.path("/auth/**")
                        .uri("lb://auth-service"))
                .build();
    }
}
