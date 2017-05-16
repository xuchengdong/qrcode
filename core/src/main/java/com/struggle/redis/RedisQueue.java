package com.struggle.redis;

import com.struggle.core.AtomicCondition;
import com.struggle.core.Queue;

import javax.validation.constraints.NotNull;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuchengdongxcd@126.com on 2016/12/2.
 */
public class RedisQueue<E> implements Queue<E> {

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    @NotNull
    private final RedisClient<E> redisClient;

    @NotNull
    private final int capacity;

    @NotNull
    private final String key;

    public RedisQueue(int capacity, String key, RedisClient<E> redisClient) {
        this.capacity = capacity;
        this.key = key;
        this.redisClient = redisClient;
    }

    public <T> T put(E obj, AtomicCondition<T> atomicCondition) throws InterruptedException {
        lock.lock();
        try {
            while (redisClient.llen(key) >= capacity) {
                notFull.await();
            }

            boolean success = atomicCondition == null;
            T t = success ? null : atomicCondition.execute();
            success = success || atomicCondition.isSuccess(t);

            if (success) {
                redisClient.rightPush(key, obj);
            }
            notEmpty.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (redisClient.llen(key) == 0) {
                notEmpty.await();
            }
            E x = redisClient.leftPop(key);
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public boolean isFull() {
        lock.lock();
        try {
            return redisClient.llen(key) >= capacity;
        } finally {
            lock.unlock();
        }
    }

    public boolean isNotFull() {
        lock.lock();
        try {
            return redisClient.llen(key) < capacity;
        } finally {
            lock.unlock();
        }
    }

    public long size() {
        lock.lock();
        try {
            return redisClient.llen(key);
        } finally {
            lock.unlock();
        }
    }
}
