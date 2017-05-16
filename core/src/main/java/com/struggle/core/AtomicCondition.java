package com.struggle.core;

/**
 * @author xuchengdongxcd@126.com on 2016/12/3.
 */
public interface AtomicCondition<T> {
    T execute();

    boolean isSuccess(T t);
}
