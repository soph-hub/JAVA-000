package org.geekbang.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer id;

    private String name;

    public static Student create(){
        return new Student(1, "student-by-static-method");
    }
}