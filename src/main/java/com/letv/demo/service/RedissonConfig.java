/*
 *  Copyright (c) 2004-2015 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.demo.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.redisson.Config;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
@Configuration
public class RedissonConfig {

    @Value("${redis.ip}")
    private String ip;
    @Value("${redis.port}")
    private String port;

    private Redisson redisson;

    @PostConstruct
    public void initRedission() throws Exception {
        // connects to single Redis server via Config
        Config config = new Config();
        config.useSingleServer().setAddress(ip + ":" + port);

        // connects to default Redis server 127.0.0.1:6379
        redisson = Redisson.create();
    }

    @PreDestroy
    public void destroy() throws Exception {
        redisson.shutdown();
    }

    public Redisson getRedisson() {
        return redisson;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }
}
