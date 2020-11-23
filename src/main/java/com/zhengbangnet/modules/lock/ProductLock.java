package com.zhengbangnet.modules.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品锁根据商品id hash值分配不同的32把锁 提高效率
 */
public class ProductLock {

    private static int LOCK_COUNT = 32;
    private static Object[] locks ;

    static {
        locks = new Object[LOCK_COUNT];
        for (int i=0;i<LOCK_COUNT;i++){
            locks[i] = new Object();
        }
    }

    private static int keyHashCode(Object k){
        return Math.abs(k.hashCode() % LOCK_COUNT);
    }

    public static Object getLock(Object k){
        int keyHashCode = keyHashCode(k);
        return locks[keyHashCode % LOCK_COUNT];
    }

}
