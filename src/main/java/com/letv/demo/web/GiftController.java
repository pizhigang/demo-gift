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
import com.letv.demo.service.DistributedAtomicLong;

/**
 *
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
@RestController
public class GiftController {

    private static final Logger log = LoggerFactory.getLogger(GiftController.class);

    @Autowired
    private DistributedAtomicLong distributedAtomicLong;

    @Autowired
    private RedisRepository repository;

    @RequestMapping("/")
    String home() {
        String response = "it works";
        log.info("response: {}", response);
        return response;
    }

    @RequestMapping("/gift")
    public String gift() {

        long gift = distributedAtomicLong.getAndDecrement();
        if (gift > 0) {

            String key = "gift" + gift;
            String value = Long.toString(gift);
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
}
