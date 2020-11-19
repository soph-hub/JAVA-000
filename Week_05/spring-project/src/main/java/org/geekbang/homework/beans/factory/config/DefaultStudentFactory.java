package org.geekbang.homework.beans.factory.config;

import org.geekbang.homework.beans.factory.StudentFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class DefaultStudentFactory implements StudentFactory, InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() {
        System.out.println("InitializingBean#afterPropertiesSet() : StudentFactory 初始化中...");
    }

    @Override
    public void destroy() {
        System.out.println("DisposableBean#destroy() : StudentFactory 销毁中...");
    }
}