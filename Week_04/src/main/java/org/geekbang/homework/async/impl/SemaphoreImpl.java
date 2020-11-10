package org.geekbang.homework.async.impl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;
import org.geekbang.homework.util.MathUtils;

/**
 * Semaphore 实现
 */
public class SemaphoreImpl {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Semaphore semaphore = new Semaphore(1);
        // 获取信号量，启动线程做计算
        semaphore.acquire();
        AtomicInteger result = new AtomicInteger();
        new Thread(() -> {
            result.set(MathUtils.fibonacci(36));
            // 计算完成后释放信号量
            semaphore.release();
        }).start();
        // 如果上次的 acquire对应的release没有执行，会阻塞等待
        semaphore.acquire();
        semaphore.release();
        // 拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

}
