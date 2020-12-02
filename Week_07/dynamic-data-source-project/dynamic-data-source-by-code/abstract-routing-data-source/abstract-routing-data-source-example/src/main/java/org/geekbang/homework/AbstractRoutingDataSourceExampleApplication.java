package org.geekbang.homework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "org.geekbang.homework.mapper")
@SpringBootApplication
public class AbstractRoutingDataSourceExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbstractRoutingDataSourceExampleApplication.class, args);
	}

}
