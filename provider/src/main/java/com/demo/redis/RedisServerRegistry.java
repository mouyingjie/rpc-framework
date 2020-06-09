package com.demo.redis;

import redis.clients.jedis.JedisPubSub;

/**
 * redis发布订阅
 * Created by chenxyz on 2020/6/2.
 */
public class RedisServerRegistry extends JedisPubSub{

    /**
     * 当往频道其实就是队列，当往里面发布消息的时候，这个方法就会触发
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
    }

    @Override
    public void subscribe(String... channels) {
        super.subscribe(channels);
    }
}
