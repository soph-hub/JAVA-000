package org.geekbang.homework.util;

import java.util.function.Function;

/**
 * @author Yaphet
 * @date 2020/11/10
 */
public class MathUtils {
    private MathUtils() {
    }

    public static int fibonacci(int a) {
        if (a < 2) {
            return 1;
        }
        return fibonacci(a - 1) + fibonacci(a - 2);
    }

    public static <T, R> R doTask(T t, Function<T, R> function){
        return function.apply(t);
    }
}
