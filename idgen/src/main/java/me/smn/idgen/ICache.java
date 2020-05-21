package me.smn.idgen;

public interface ICache {
    long get(String key);

    void setForIncrNx(String key, long value);

    boolean exists(String k);

    long incr(String k);

    void del(String k);
}
