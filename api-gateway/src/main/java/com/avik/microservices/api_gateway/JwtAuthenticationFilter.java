package com.avik.microservices.api_gateway;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        log.info("========== JWT FILTER TRIGGERED ==========");
        log.info("Path: {}", path);
        log.info("Method: {}", exchange.getRequest().getMethod());


        // Skip JWT only for login and register
        if (path.startsWith("/api/auth/login") ||
                path.startsWith("/api/auth/register")) {

            log.info("Skipping auth for login/register endpoint");
            return chain.filter(exchange);
        }


        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");

        log.info("Authorization Header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn(" UNAUTHORIZED - Missing or invalid Authorization header");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        log.info("Extracted token: {}", token.substring(0, Math.min(20, token.length())) + "...");

        if (!jwtService.isTokenValid(token)) {
            log.warn(" UNAUTHORIZED - Invalid JWT token");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        Claims claims = jwtService.extractAllClaims(token);
        String role = claims.get("role", String.class);
        String email = claims.getSubject();
        String userId = claims.get("userId", String.class);

        log.info(" Token valid - User: {}, Role: {}, UserId: {}", email, role, userId);

        if (role == null || email == null || userId == null) {
            log.warn(" UNAUTHORIZED - Missing claims in token");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String requiredRole = getRequiredRole(path);
        log.info("Required role for {}: {}", path, requiredRole);

        if (requiredRole != null && !hasRequiredRole(role, requiredRole)) {
            log.warn(" FORBIDDEN - User {} with role {} cannot access {}",
                    email, role, path);
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        log.info(" Access granted - Forwarding request");

        // Forward user info to downstream services
        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Email", email)
                .header("X-User-Role", role)
                .header("X-User-Id", userId)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean hasRequiredRole(String userRole, String requiredRole) {
        if ("ADMIN".equals(userRole)) {
            return true;
        }
        return requiredRole.equals(userRole);
    }

    private String getRequiredRole(String path) {
        if (path.startsWith("/api/products") ||
                path.startsWith("/api/inventory")) {
            return "ADMIN";
        }

        if (path.startsWith("/api/orders")) {
            return "USER";
        }

        return null;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}