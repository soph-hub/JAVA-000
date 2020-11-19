package org.geekbang.homework.async.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.geekbang.homework.utils.MathUtils;

/**
 * ThreadPool.submit 实现
 */
public class ThreadPoolSubmitImpl {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 创建线程池异步执行
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        Future<Integer> future = singleThreadExecutor.submit(() -> MathUtils.fibonacci(36));
        // 拿到result 并输出
        System.out.println("异步计算结果为：" + future.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        singleThreadExecutor.shutdown();
    }

}
