package com.train.lock.distribute.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Jedis;

/**
 * @author piaohailin
 * @date   2014-2-28
*/
public class TestRedis {

    /**
     * @param args
     * @author piaohailin
     * @date   2014-2-28
    */
    public static void main(String[] args) {
        Jedis jedis   = new Jedis("localhost", 6379);
        jedis.connect();
        for (int i = 0; i < 10; i++) {
            System.out.println(jedis.setnx("m", "p"));//1设置成功，0设置失败(值已经存在)
        }
    }

}