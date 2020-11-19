package org.geekbang.homework.async.impl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.geekbang.homework.utils.MathUtils;

/**
 * await && signal 实现
 */
public class AwaitSignalImpl {
    private volatile Integer result = null;
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public void setResult(){
        lock.lock();
        try {
            result = MathUtils.fibonacci(36);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int getResult() throws InterruptedException {
        lock.lock();
        try {
            while (result == null) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        AwaitSignalImpl impl = new AwaitSignalImpl();
        // 计算前先加锁（否则可能会先执行getResult中的lock）
        lock.lock();
        // 创建线程，异步执行
        Thread task = new Thread(impl::setResult);
        task.start();

        // 拿到result 并输出
        System.out.println("异步计算结果为：" + impl.getResult());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }
}
