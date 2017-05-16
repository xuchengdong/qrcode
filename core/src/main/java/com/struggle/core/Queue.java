package com.struggle.core;

/**
 * @author xuchengdongxcd@126.com on 2016/12/2.
 */
public interface Queue<E> {

    <T> T put(E obj, AtomicCondition<T> atomicCondition) throws InterruptedException;

    E take() throws InterruptedException;

    boolean isFull();

    boolean isNotFull();

    long size();
}
