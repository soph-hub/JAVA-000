package org.geekbang.homework.async.impl;

import java.util.concurrent.atomic.AtomicInteger;
import org.geekbang.homework.utils.MathUtils;

/**
 * ThreadJoin 实现
 */
public class ThreadJoinImpl {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        AtomicInteger result = new AtomicInteger();
        // 创建线程，异步执行
        Thread thread = new Thread(() -> result.set(MathUtils.fibonacci(36)));
        thread.start();
        thread.join();
        // 拿到result 并输出
        System.out.println("异步计算结果为：" + result.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }
}
