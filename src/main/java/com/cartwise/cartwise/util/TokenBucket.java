package com.cartwise.cartwise.util;

import java.time.Clock;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucket {
    private final long capacity;
    private final double refillTokensPerSecond;
    private final Clock clock;

    private double tokens;
    private long lastRefillNanos;
    private final ReentrantLock lock = new ReentrantLock();


    public TokenBucket(long capacity, double refillTokensPerSecond) {
        this.capacity = capacity;
        this.refillTokensPerSecond = refillTokensPerSecond;
        this.clock = Clock.systemUTC();
    }

    public TokenBucket(long capacity, double refillTokensPerSecond, Clock clock) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        if (refillTokensPerSecond <= 0) throw new IllegalArgumentException("refillTokensPerSecond must be > 0");
        this.capacity = capacity;
        this.refillTokensPerSecond = refillTokensPerSecond;
        this.clock = clock;

        this.tokens = capacity;
        this.lastRefillNanos = nowNanos();
    }

    public boolean tryConsume(long requestedTokens) {
        if (requestedTokens <= 0) throw new IllegalArgumentException("requestedTokens must be > 0");

        lock.lock();
        try {
            refillIfNeeded();

            if (tokens >= requestedTokens) {
                tokens -= requestedTokens;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public double getAvailableTokens() {
        lock.lock();
        try {
            refillIfNeeded();
            return tokens;
        } finally {
            lock.unlock();
        }
    }


    private void refillIfNeeded() {
        long now = nowNanos();
        long elapsedNanos = now - lastRefillNanos;
        if (elapsedNanos <= 0) return;

        double elapsedSeconds = elapsedNanos / 1_000_000_000.0;
        double refill = elapsedSeconds * refillTokensPerSecond;

        if (refill > 0) {
            tokens = Math.min(capacity, tokens + refill);
            lastRefillNanos = now;
        }
    }

    private long nowNanos() {
        return clock.millis() * 1_000_000L;
    }
}