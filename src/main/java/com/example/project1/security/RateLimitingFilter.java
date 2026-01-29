package com.example.project1.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter implements Filter {

    // Mapa w pamięci: IP użytkownika -> Jego osobiste wiadro
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Pobieramy IP klienta
        String ip = httpRequest.getRemoteAddr();

        // tworzymy) wiadro dla tego IP
        Bucket bucket = cache.computeIfAbsent(ip, this::createNewBucket);

        // Próba pobrania 1 żetonu
        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            // Porażka: brak żetonów -> Blokujemy
            httpResponse.setStatus(429); // Kod 429: Too Many Requests
            httpResponse.getWriter().write("ZA DUZO ZADAN! Zwolnij troche (Rate Limit Exceeded)");
        }
    }

    // Konfiguracja limitu
    private Bucket createNewBucket(String key) {
        // Limit: 10 żądań na 1 minutę
        Bandwidth limit = Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}