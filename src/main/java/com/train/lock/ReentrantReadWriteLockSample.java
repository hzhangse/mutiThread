package com.train.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: yanxuxin
 * @date: 2010-1-7
 */
public class ReentrantReadWriteLockSample {

	public static void main(String[] args) {
		testReadLock();
		// testWriteLock();
	}

	public static void testReadLock() {
		final ReadWriteLockSampleSupport support = new ReadWriteLockSampleSupport();
		support.initCache();

		Runnable runnable = new Runnable() {
			public void run() {
				support.get("test");
			}
		};

		new Thread(runnable).start();
		new Thread(runnable).start();

		new Thread(new Runnable() {
			public void run() {
				support.put("test", "test");
			}
		}).start();
	}

	public static void testWriteLock() {
		final ReadWriteLockSampleSupport support = new ReadWriteLockSampleSupport();
		support.initCache();

		new Thread(new Runnable() {
			public void run() {
				support.put("key1", "value1");
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				support.put("key2", "value2");
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				support.get("key1");
			}
		}).start();
	}
}

class ReadWriteLockSampleSupport {
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();

	private volatile boolean completed;
	private Map<String, String> cache;

	public void initCache() {
		readLock.lock();
		if (!completed) {
			// Must release read lock before acquiring write lock
			readLock.unlock(); // (1)
			writeLock.lock(); // (2)
			if (!completed) {
				cache = new HashMap<String, String>(32);
				completed = true;
			}
			// Downgrade by acquiring read lock before releasing write lock
			readLock.lock(); // (3)
			writeLock.unlock(); // (4) Unlock write, still hold read
		}

		System.out.println("empty? " + cache.isEmpty());
		readLock.unlock();
	}

	public String get(String key) {
		readLock.lock();
		System.out.println(Thread.currentThread().getName() + " read.");
		startTheCountdown();
		try {
			return cache.get(key);
		} finally {
			readLock.unlock();
		}
	}

	public String put(String key, String value) {
		writeLock.lock();
		System.out.println(Thread.currentThread().getName() + " write.");
		startTheCountdown();
		try {
			return cache.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * A simple countdown,it will stop after about 5s.
	 */
	public void startTheCountdown() {
		long currentTime = System.currentTimeMillis();
		for (;;) {
			long diff = System.currentTimeMillis() - currentTime;
			if (diff > 5000) {
				break;
			}
		}
	}
}