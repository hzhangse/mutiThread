package com.train.lock.distribute.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisPool;

/**
 * @author piaohailin
 * @date   2014-3-13
*/
public class SimpleLockTest {

    /**
     * @param args
     * @author piaohailin
     * @date   2014-3-13
    */
    public static void main(String[] args) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        final JedisPool pool = new JedisPool(poolConfig, "localhost", 6379, 3000); //���һ������Ϊ����
        SimpleLock.setPool(pool);//ֻ��Ҫ��ʼ��һ��
        
        String key = "test";
        SimpleLock lock = new SimpleLock(key);
        lock.wrap(new Runnable() {
            @Override
            public void run() {
                //�˴����������ϵ�
            }
        });
    }

}