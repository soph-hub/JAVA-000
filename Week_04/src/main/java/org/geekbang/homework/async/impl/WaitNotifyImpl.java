package org.geekbang.homework.async.impl;

import org.geekbang.homework.utils.MathUtils;

/**
 * wait && notify 实现 （join 底层实现）
 */
public class WaitNotifyImpl {
    private volatile Integer result = null;

    public void setResult(){
        result = MathUtils.fibonacci(36);
        notifyAll();
    }

    public synchronized int getResult() throws InterruptedException {
        while (result == null){
            wait();
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        WaitNotifyImpl impl = new WaitNotifyImpl();
        // 创建线程，异步执行
        Thread task = new Thread(impl::setResult);
        task.start();
        // 拿到result 并输出
        System.out.println("异步计算结果为：" + impl.getResult());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

}
