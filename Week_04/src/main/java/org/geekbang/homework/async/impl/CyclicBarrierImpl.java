package org.geekbang.homework.async.impl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import org.geekbang.homework.util.MathUtils;

/**
 * CyclicBarrier 实现
 */
public class CyclicBarrierImpl {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        AtomicInteger result = new AtomicInteger();
        // 创建 cyclicBarrier 对象
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, () -> {
            // 拿到result 并输出
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        });

        // 启动线程计算，并赋值给 result，通知回掉方法
        new Thread(() -> {
            result.set(MathUtils.fibonacci(36));
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

    }

}
