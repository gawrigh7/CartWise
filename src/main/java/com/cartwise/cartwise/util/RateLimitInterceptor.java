package com.cartwise.cartwise.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final TokenBucketManager manager;

    public RateLimitInterceptor() {
        long capacity = 3;
        double refillPerSecond = 3.0 / 86400.0;
        long ttlMs = 48L * 60L * 60L * 1000L;
        this.manager = new TokenBucketManager(capacity, refillPerSecond, ttlMs);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (!path.startsWith("/api/recipes/generate")) {
            return true;
        }


        String userKey = resolveUserKey(request);
        if (userKey == null) {
            userKey = "anon:" + request.getRemoteAddr();
        }

        boolean allowed = manager.tryConsume(userKey, 1);
        if (allowed) {
            response.setHeader("X-RateLimit-Remaining", String.valueOf((int) Math.floor(manager.availableTokens(userKey))));
            return true;
        }

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");
        response.getWriter().write("""
            {"error":"RATE_LIMITED","message":"Too many requests. Please try again later."}
        """);
        return false;
    }

    private String resolveUserKey(HttpServletRequest request) {
        return request.getHeader("X-User-Id");
    }
}

