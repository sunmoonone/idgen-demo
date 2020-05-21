package me.smn.idgen;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheImpl implements ICache {
    final private Map<String, Long> store = new ConcurrentHashMap<>();

    @Override
    public long get(String key) {
        return store.getOrDefault(key, 0L);
    }

    @Override
    public void setForIncrNx(String key, long value) {
        store.putIfAbsent(key, value);
    }

    @Override
    public boolean exists(String k) {
        return store.containsKey(k);
    }

    @Override
    public long incr(String k) {
        return store.computeIfPresent(k, (key, val) -> {
            return val + 1;
        });
    }

    @Override
    public void del(String k) {
        store.remove(k);
    }
}
