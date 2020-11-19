package org.geekbang.homework.beans.factory.config;

import org.geekbang.homework.entity.Student;
import org.springframework.beans.factory.FactoryBean;

public class StudentFactoryBean implements FactoryBean<Student> {

    @Override
    public Student getObject() {
        return Student.create();
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }
}
