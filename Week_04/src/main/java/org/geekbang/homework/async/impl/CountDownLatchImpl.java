package org.geekbang.homework.async.impl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.geekbang.homework.util.MathUtils;

/**
 * CountDownLatch 实现
 */
public class CountDownLatchImpl {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        // 创建 countDownLatch 对象
        CountDownLatch countDownLatch =  new CountDownLatch(1);
        AtomicInteger result = new AtomicInteger();
        // 启动线程计算，并赋值，释放 countDownLatch 对象
        new Thread(() -> {
            result.set(MathUtils.fibonacci(36));
            countDownLatch.countDown();
        }).start();
        // 注意跟 CyclicBarrier 不同，这里在主线程 await
        countDownLatch.await();
        // 拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

}
