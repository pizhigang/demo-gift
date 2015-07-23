/*
 *  Copyright (c) 2004-2015 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.letv.demo.service.RedissonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * <code>
 * java -jar target/demo-1.0-SNAPSHOT.jar --redis.ip=localhost --redis.port=6379 --gift.count=10000
 *
 * java -jar target/demo-1.0-SNAPSHOT.jar --debug --redis.ip=localhost --redis.port=6379 --gift.count=10000
 * 
 * ./wrk -t4 -c100 -d10s http://localhost:8080/gift/
 * 
 * ./wrk -t4 -c100 -d2s http://localhost:8080/gift/1
 * </code>
 *
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
//@EnableAutoConfiguration
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Example {

    private static final Logger log = LoggerFactory.getLogger(Example.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Example.class, args);

        //　init the system status
        RedissonService dAtomicLong = ctx.getBean(RedissonService.class);
        dAtomicLong.init();
        log.info("set the {} = {}", RedissonService.NAME_GIFT_COUNT, dAtomicLong.getGiftCount());

    }
}
