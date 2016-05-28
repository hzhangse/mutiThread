
package com.train.lock.distribute.redis;

import redis.clients.jedis.Jedis;

/**
 * Redis distributed lock implementation.
 * 
 * @author Alois Belaska <alois.belaska@gmail.com>
 */
public class JedisLock {
	
	Jedis	jedis;
	
	/**
	 * Lock key path.
	 */
	String	lockKey;
	
	/**
	 * Lock expiration in miliseconds.
	 */
	int	expireMsecs	= 60 * 1000; // ����ʱ����ֹ�߳��������Ժ����޵�ִ�еȴ�
	                                     
	/**
	 * Acquire timeout in miliseconds.
	 */
	// int timeoutMsecs = 10 * 1000; // ���ȴ�����ֹ�̼߳���
	
	boolean	locked	    = false;
	
	/**
	 * Detailed constructor with default acquire timeout 10000 msecs and lock expiration of 60000 msecs.
	 * 
	 * @param jedis
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 */
	public JedisLock(Jedis jedis, String lockKey) {
		this.jedis = jedis;
		this.lockKey = lockKey;
	}
	
	/**
	 * Detailed constructor.
	 * 
	 * @param jedis
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 * @param timeoutSecs
	 *            acquire timeout in miliseconds (default: 10000 msecs)
	 * @param expireMsecs
	 *            lock expiration in miliseconds (default: 60000 msecs)
	 */
	public JedisLock(Jedis jedis, String lockKey, int expireMsecs) {
		this(jedis, lockKey);
		this.expireMsecs = expireMsecs;
	}
	
	/**
	 * Detailed constructor with default acquire timeout 10000 msecs and lock expiration of 60000 msecs.
	 * 
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 */
	public JedisLock(String lockKey) {
		this(null, lockKey);
	}
	
	/**
	 * Detailed constructor with default lock expiration of 60000 msecs.
	 * 
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 * @param timeoutSecs
	 *            acquire timeout in miliseconds (default: 10000 msecs)
	 */
	// public JedisLock(String lockKey, int timeoutMsecs) {
	// this(null, lockKey, timeoutMsecs);
	// }
	
	/**
	 * @return lock key
	 */
	public String getLockKey() {
		return lockKey;
	}
	
	/**
	 * Acquire lock.
	 * 
	 * @param jedis
	 * @return true if lock is acquired, false acquire timeouted
	 * @throws InterruptedException
	 *             in case of thread interruption
	 */
	public synchronized boolean acquire() throws InterruptedException {
		return acquire(jedis);
		
	}
	
	public synchronized boolean acquire(long time) throws InterruptedException {
		return acquire(jedis, time);
		
	}
	
	/**
	 * Acquire lock.
	 * 
	 * @param jedis
	 * @return true if lock is acquired, false acquire timeouted
	 * @throws InterruptedException
	 *             in case of thread interruption
	 */
	public synchronized boolean acquire(Jedis jedis) throws InterruptedException {
		return acquire(jedis, -1);
	}
	
	public synchronized boolean acquire(Jedis jedis, long timeout) throws InterruptedException {
		if (timeout <= 0) {
			timeout = Long.MAX_VALUE;
		}
		while (timeout >= 0) {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			String expiresStr = String.valueOf(expires); // ������ʱ��
			
			if (jedis.setnx(lockKey, expiresStr) == 1) {
				// lock acquired
				locked = true;
				return true;
			}
			
			String currentValueStr = jedis.get(lockKey); // redis���ʱ��
			if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				// �ж��Ƿ�Ϊ�գ���Ϊ�յ�����£�����������߳�������ֵ����ڶ��������ж��ǹ���ȥ��
				// lock is expired
				
				String oldValueStr = jedis.getSet(lockKey, expiresStr);
				// ��ȡ��һ��������ʱ�䣬���������ڵ�������ʱ�䣬
				// ֻ��һ���̲߳��ܻ�ȡ��һ�����ϵ�����ʱ�䣬��Ϊjedis.getSet��ͬ����
				if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
					// ������ʱ�򣬶���߳�ǡ�ö������������ֻ��һ���̵߳�����ֵ�͵�ǰֵ��ͬ��������Ȩ����ȡ��
					// lock acquired
					locked = true;
					return true;
				}
			}
			timeout -= 100;
			Thread.sleep(100);
		}
		return false;
	}
	
	/**
	 * Acqurired lock release.
	 */
	public synchronized void release() {
		release(jedis);
	}
	
	/**
	 * Acqurired lock release.
	 */
	public synchronized void release(Jedis jedis) {
		if (locked) {
			String currentValueStr = jedis.get(lockKey); // redis���ʱ��
			
			long nowTime = System.currentTimeMillis();
			if (currentValueStr != null && Long.parseLong(currentValueStr) > nowTime) {
				jedis.del(lockKey);
				
			}
			locked = false;
		}
	}
}