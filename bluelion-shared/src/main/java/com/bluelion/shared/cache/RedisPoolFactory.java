package com.bluelion.shared.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisPoolFactory {

    @Autowired
    RedisConfig  redisConfig;

    /**
     * 将redis连接池注入spring容器
     * @return
     */
    @Bean("jedisPool")
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisConfig.getPoolMaxIdle());
        config.setMaxTotal(redisConfig.getPoolMaxTotal());
        config.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        JedisPool jp = null;
        if (redisConfig.getPassword() != null && !"".equals(redisConfig.getPassword())) {
            jp = new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(),
                    redisConfig.getTimeout()*1000, redisConfig.getPassword(), 0);
        } else {
           jp = new JedisPool(new JedisPoolConfig(), redisConfig.getHost(), redisConfig.getPort());
        }

        return jp;
    }
}
