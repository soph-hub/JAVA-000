package org.geekbang.homework.beans.factory;

import org.geekbang.homework.entity.Student;

public interface StudentFactory {

    default Student create() {
        return Student.create();
    }

    default Student instance() {
        return new Student(1, "student-by-instance-method");
    }

}