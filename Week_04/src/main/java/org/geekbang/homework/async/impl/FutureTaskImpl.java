package org.geekbang.homework.async.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.geekbang.homework.utils.MathUtils;

/**
 * FutureTask.get 实现
 */
public class FutureTaskImpl {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 执行 Callable 方式，需要 FutureTask 实现类的支持，用于接受运算结果。
        FutureTask<Integer> futureTask = new FutureTask<>(() -> MathUtils.fibonacci(36));
        // 启动线程
        new Thread(futureTask).start();
        // 接收线程运算后的结果，拿到 result 并输出
        System.out.println("异步计算结果为：" + futureTask.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

}
