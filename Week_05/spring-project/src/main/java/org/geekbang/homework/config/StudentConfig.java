package org.geekbang.homework.config;

import org.geekbang.homework.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean(name = "student-by-configuration-java-bean")
    public Student getStudent(){
        return new Student().setId(1).setName("student-by-configuration-java-bean");
    }

}
