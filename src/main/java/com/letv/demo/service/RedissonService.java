/*
 *  Copyright (c) 2004-2015 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.demo.service;

import org.redisson.Redisson;
import org.redisson.core.RAtomicLong;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 *
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
@Service
@Configuration
public class RedissonService {

    public static final String NAME_GIFT_COUNT = "giftCount";
    public static final String NAME_GIFT_LOCK = "giftLock";

    @Value("${gift.count}")
    private long giftCount;

    @Autowired
    private RedissonConfig redissonConfig;

    public void init() {
        Redisson redisson = redissonConfig.getRedisson();
        RAtomicLong atomicLong = redisson.getAtomicLong(NAME_GIFT_COUNT);
        atomicLong.set(giftCount);
    }

    public long getAndDecrement() {
        Redisson redisson = redissonConfig.getRedisson();
        return redisson.getAtomicLong(NAME_GIFT_COUNT).getAndDecrement();
    }

    public RLock getLock() {
        Redisson redisson = redissonConfig.getRedisson();
        RLock lock = redisson.getLock(NAME_GIFT_LOCK);
        return lock;
    }

    /**
     * @return the giftCount
     */
    public long getGiftCount() {
        return giftCount;
    }

    /**
     * @param giftCount the giftCount to set
     */
    public void setGiftCount(long giftCount) {
        this.giftCount = giftCount;
    }
}
