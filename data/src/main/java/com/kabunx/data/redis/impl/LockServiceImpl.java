package com.kabunx.data.redis.impl;

import com.kabunx.data.redis.LockService;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

@Slf4j
public class LockServiceImpl implements LockService {
    private static final String LOCK_SUCCESS = "OK";

    private static final String UNLOCK_SUCCESS = "1";

    private static final String prefix = "redis:lock:"; // 锁键

    protected long internalLockLeaseTime = 30000; // 锁过期时间ms

    // 参数信息
    SetParams params = SetParams.setParams().nx().px(internalLockLeaseTime);

    JedisPool jedisPool;

    public LockServiceImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 一次性加锁
     */
    @Override
    public boolean lock(String key, String identity) {
        try (Jedis jedis = jedisPool.getResource()) {
            // SET命令返回OK ，则证明获取锁成功
            String lock = jedis.set(prefix + key, identity, params);
            return LOCK_SUCCESS.equals(lock);
        }
    }

    /**
     * 解锁
     */
    @Override
    public boolean unlock(String key, String identity) {
        try (Jedis jedis = jedisPool.getResource()) {
            String script =
                    "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                            "   return redis.call('del',KEYS[1]) " +
                            "else" +
                            "   return 0 " +
                            "end";
            Object result = jedis.eval(
                    script,
                    Collections.singletonList(prefix + key),
                    Collections.singletonList(identity)
            );
            return UNLOCK_SUCCESS.equals(result.toString());
        }
    }
}
