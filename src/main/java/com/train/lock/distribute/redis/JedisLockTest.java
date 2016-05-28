
package com.train.lock.distribute.redis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author piaohailin
 * @date   2014-3-1
*/
public class JedisLockTest {
	private static ExecutorService	executor	= Executors.newFixedThreadPool(100);
	
	//@Test
	public void TestJedisLockMockMutiVM() throws InterruptedException {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		final JedisPool pool = new JedisPool(poolConfig, "localhost", 6379); // 最后一个参数为密码
		final String key = "h";
		final  int clientNum = 3;
		final CountDownLatch latch = new CountDownLatch(clientNum);
		
		for (int i = 0; i < clientNum; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Jedis jedis = pool.getResource();
						JedisLock lock = new JedisLock(jedis, key, 2000);
						if (lock.acquire()) {// 如果锁上了
							try {
								System.out.println("curent client :"+Thread.currentThread().getName()+ " *** get lock time:"+System.currentTimeMillis());
								Thread.sleep(4000);
								
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								lock.release();// 则解锁
							}
						}
					} catch (Throwable t) {
						t.printStackTrace();
					} finally {
						latch.countDown();
					}
				}
			});
		}
		latch.await();
		executor.shutdown();
		pool.close();
	}
	
	@Test
	public void TestJedisLockMockMutiVMUsingAcquireTime() throws InterruptedException {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		final JedisPool pool = new JedisPool(poolConfig, "localhost", 6379); // 最后一个参数为密码
		final String key = "h";
		final  int clientNum = 3;
		final CountDownLatch latch = new CountDownLatch(clientNum);
		
		for (int i = 0; i < clientNum; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Jedis jedis = pool.getResource();
						JedisLock lock = new JedisLock(jedis, key, 2000);
						if (lock.acquire(3000)) {// 如果锁上了
							try {
								System.out.println("curent client :"+Thread.currentThread().getName()+ " *** get lock time:"+System.currentTimeMillis());
								Thread.sleep(4000);
								
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								lock.release();// 则解锁
							}
						}
					} catch (Throwable t) {
						t.printStackTrace();
					} finally {
						latch.countDown();
					}
				}
			});
		}
		latch.await();
		executor.shutdown();
		pool.close();
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		// final JedisPool pool = new JedisPool("192.168.56.2", 6379);
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		final JedisPool pool = new JedisPool(poolConfig, "localhost", 6379); // 最后一个参数为密码
		final String key = "h";
		final  int clientNum = 3;
		final CountDownLatch latch = new CountDownLatch(clientNum);
		
		for (int i = 0; i < clientNum; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Jedis jedis = pool.getResource();
						JedisLock lock = new JedisLock(jedis, key, 60000);
						if (lock.acquire()) {// 如果锁上了
							try {
								Thread.sleep(1000);
								System.out.println(System.currentTimeMillis());
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								lock.release();// 则解锁
							}
						}
					} catch (Throwable t) {
						t.printStackTrace();
					} finally {
						latch.countDown();
					}
				}
			});
		}
		latch.await();
		executor.shutdown();
		pool.close();
	}
	
}