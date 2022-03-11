package com.kabunx.data.redis;

public interface LockService {
    boolean lock(String key, String identity);

    boolean unlock(String key, String identity);
}
