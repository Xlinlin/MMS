package org.xiao.message.redis;

import redis.clients.jedis.JedisPoolConfig;

public class MessageJedisPoolConfig extends JedisPoolConfig
{

    /**
     * 默认最大链接数
     */
    public static final int DEFAULT_MAX_TOTAL = 1024;
    /**
     * 默认最大空闲数
     */
    private static final int DEFAULT_MAX_IDLE = 200;

    public static final int DEFAULT_NUM_TESTS_PER_EVICTION_RUN = 1024;

    public static final int DEFAULT_MAX_WAIT_MILLIS = 1000;

    public static final boolean DEFAULT_TEST_ON_BORROW = true;

    public static final int DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 30000;

    public static final int DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 30000;

    public static final int DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 10000;

    public static final boolean DEFAULT_BLOCK_WHEN_EXHAUSTED = true;

    public MessageJedisPoolConfig()
    {
        init();
    }

    /**
     *
     */
    public void init()
    {
        setMaxTotal(DEFAULT_MAX_TOTAL);
        setMaxIdle(DEFAULT_MAX_IDLE);
        setNumTestsPerEvictionRun(DEFAULT_NUM_TESTS_PER_EVICTION_RUN);
        setBlockWhenExhausted(DEFAULT_BLOCK_WHEN_EXHAUSTED);
        setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
        setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        setNumTestsPerEvictionRun(DEFAULT_NUM_TESTS_PER_EVICTION_RUN);
        setTestOnBorrow(false);
        setTestWhileIdle(true);
        setTestOnReturn(false);
        setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        setSoftMinEvictableIdleTimeMillis(DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);

    }

}
