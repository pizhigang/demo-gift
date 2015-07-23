/*
 *  Copyright (c) 2004-2015 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.demo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * <pre>
 * wget http://download.redis.io/releases/redis-3.0.3.tar.gz
 * tar xzf redis-3.0.3.tar.gz
 * cd redis-3.0.3
 * make
 * src/redis-server
 * 
 * <another console>
 * src/redis-cli flushall
 * </pre>
 *
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
@Repository
public class RedisRepository {

        private static final Logger log = LoggerFactory.getLogger(RedisRepository.class);

    private final StringRedisTemplate template;

    @Autowired
    public RedisRepository(StringRedisTemplate template) {
        this.template = template;
    }

    public void setString(String key, String value) {
        log.info("set {} = {}", key, value);
        
        template.opsForValue().set(key, value);
    }

    public String getString(String key) {
        return template.opsForValue().get(key);
    }
}
