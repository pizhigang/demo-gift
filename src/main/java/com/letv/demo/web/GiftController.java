/*
 *  Copyright (c) 2004-2015 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.letv.demo.repository.RedisRepository;
import com.letv.demo.service.RedissonService;
import java.util.concurrent.TimeUnit;
import org.redisson.core.RLock;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
@RestController
public class GiftController {

    private static final Logger log = LoggerFactory.getLogger(GiftController.class);

    @Autowired
    private RedissonService redissonService;

    @Autowired
    private RedisRepository repository;

    /**
     * api for test if it works or not?
     *
     * @return
     */
    @RequestMapping("/")
    String home() {
        String response = "it works";
        log.info("response: {}", response);
        return response;
    }

    /**
     * api to be call by client to mock DDos, and see where the system will go.
     *
     * @return
     */
    @RequestMapping("/gift")
    public String gift() {

        long gift = redissonService.getAndDecrement();
        if (gift > 0) {
            // make sure only RedissonService.giftCount request will pass through the cache counter layer

            String key = "gift" + gift;
            String value = Long.toString(gift);

            // if use RDBMS, eg., mysql
            // we should push the data into mq 
            // then let the consumer do below -- persist to disk
            repository.setString(key, value);
            log.info("persistent the status: {} = {}", key, value);

            // get the value back and return
            String back = repository.getString(key);

            return key + ": " + back;
        } else {
            log.info("all gifts are consumed.");

            return "NOT-FOUND";
        }
    }

    /**
     * Close to the reality use case, one use can only get one gift.
     *
     * @param uid
     * @return
     */
    @RequestMapping("/gift/{uid}")
    public String gift(@PathVariable String uid) {

        long gift = redissonService.getAndDecrement();
        if (gift > 0) {
            // make sure only RedissonService.giftCount request will pass through the cache counter layer

            String key = uid;

            // has (s)he been got it?
            RLock lock = redissonService.getLock();
            // Lock time-to-live support
            // releases lock automatically after 10 seconds if unlock method not invoked
            lock.lock(500, TimeUnit.MILLISECONDS);

            try {
                String existed = repository.getString(key);
                if (existed != null) {
                    log.info("User.uid = {} has got the gift: {}", key, existed);

                    return "ALREADY-GOT";
                } else {

                    // if use RDBMS, eg., mysql
                    // we should push the data into mq 
                    // then let the consumer do below -- persist to disk
                    String value = Long.toString(gift);
                    repository.setString(key, value);
                    log.info("persistent the status: {} = {}", key, value);

                    // get the value back and return
                    String back = repository.getString(key);

                    return key + ": " + back;
                }
            } finally {
                lock.unlock();
            }
        } else {
            log.info("all gifts are consumed.");

            return "NOT-FOUND";
        }
    }
}
