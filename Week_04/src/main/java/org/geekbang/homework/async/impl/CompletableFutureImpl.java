package org.geekbang.homework.async.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.geekbang.homework.util.MathUtils;

/**
 * CompletableFuture.supplyAsync 实现
 */
public class CompletableFutureImpl {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        // get 方法会使当前线程阻塞,并且等待直到 future 完成,并且将返回 future 的值
        int result = CompletableFuture.supplyAsync(() -> MathUtils.fibonacci(36)).get();

        // join 方法与 get 并无太大差别,但 join 方法不能被打断
        // int result = CompletableFuture.supplyAsync(() -> MathUtils.fibonacci(36)).join();

        // 拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

}