package com.cartwise.cartwise.util;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TokenBucketManager {

    private static final class Entry {
        final TokenBucket bucket;
        volatile long lastSeenEpochMs;

        Entry(TokenBucket bucket, long nowMs) {
            this.bucket = bucket;
            this.lastSeenEpochMs = nowMs;
        }
    }

    private final long capacity;
    private final double refillTokensPerSecond;
    private final long ttlMs;

    private final Map<String, Entry> buckets = new ConcurrentHashMap<>();

    public TokenBucketManager(long capacity, double refillTokensPerSecond, long ttlMs) {
        this.capacity = capacity;
        this.refillTokensPerSecond = refillTokensPerSecond;
        this.ttlMs = ttlMs;
    }

    public boolean tryConsume(String key, long tokens) {
        long now = Instant.now().toEpochMilli();
        Entry entry = buckets.computeIfAbsent(key, k -> new Entry(new TokenBucket(capacity, refillTokensPerSecond), now));
        entry.lastSeenEpochMs = now;

        cleanupIfNeeded(now);

        return entry.bucket.tryConsume(tokens);
    }

    public double availableTokens(String key) {
        Entry entry = buckets.get(key);
        return entry == null ? capacity : entry.bucket.getAvailableTokens();
    }

    private void cleanupIfNeeded(long nowMs) {
        if ((nowMs & 0xFF) != 0) return;

        for (var it = buckets.entrySet().iterator(); it.hasNext(); ) {
            var e = it.next();
            if (nowMs - e.getValue().lastSeenEpochMs > ttlMs) {
                it.remove();
            }
        }
    }
}
