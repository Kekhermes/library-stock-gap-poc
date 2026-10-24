package com.library.stockgap.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class WebSecurityConfig {

    @Value("${stockgap.worker-token:worker-internal-secret-token}")
    private String workerToken;

    @Bean
    public OncePerRequestFilter workerSecurityFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                String path = request.getRequestURI();
                String authHeader = request.getHeader("Authorization");

                // Permettre l'accès aux requêtes cross-origin pour le développement local
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
                response.setHeader("Access-Control-Allow-Headers", "*");

                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    return;
                }

                // Boundary 1: /internal/** requires Worker token
                if (path.startsWith("/internal/")) {
                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/problem+json");
                        response.getWriter().write("{\"type\":\"UNAUTHORIZED\",\"title\":\"Worker token required\"}");
                        return;
                    }
                    String token = authHeader.substring(7).trim();
                    if (!workerToken.equals(token)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/problem+json");
                        response.getWriter().write("{\"type\":\"UNAUTHORIZED\",\"title\":\"Invalid worker token\"}");
                        return;
                    }
                }

                // Boundary 2: Worker token on /api/** gives 403 Forbidden
                if (path.startsWith("/api/")) {
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        String token = authHeader.substring(7).trim();
                        if (workerToken.equals(token)) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/problem+json");
                            response.getWriter().write("{\"type\":\"FORBIDDEN\",\"title\":\"Worker token cannot access Admin API\"}");
                            return;
                        }
                    }
                }

                filterChain.doFilter(request, response);
            }
        };
    }
}
