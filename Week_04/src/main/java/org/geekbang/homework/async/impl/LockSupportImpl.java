package org.geekbang.homework.async.impl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import org.geekbang.homework.utils.MathUtils;

/**
 * LockSupport 实现
 */
public class LockSupportImpl {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        AtomicInteger result = new AtomicInteger();
        Thread main = Thread.currentThread();
        new Thread(() -> {
            result.set(MathUtils.fibonacci(36));
            LockSupport.unpark(main);
        }).start();
        LockSupport.park();
        // 拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

}
