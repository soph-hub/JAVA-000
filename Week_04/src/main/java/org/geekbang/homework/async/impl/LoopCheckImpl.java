package org.geekbang.homework.async.impl;

import org.geekbang.homework.util.MathUtils;

/**
 * LoopLookup 循环检查 实现
 */
public class LoopCheckImpl {
    private volatile Integer result;

    public void setResult(){
        result = MathUtils.fibonacci(36);
    }

    public int getResult(){
        for (;;){
            if(result != null){
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        LoopCheckImpl impl = new LoopCheckImpl();
        Thread task = new Thread(impl::setResult);
        task.start();
        // 拿到result 并输出
        System.out.println("异步计算结果为：" + impl.getResult());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }
}
