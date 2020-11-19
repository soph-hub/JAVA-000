package org.geekbang.homework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootProjectTests {

	@BeforeEach // 类似于junit4的@Before
	public void before() {
		System.out.println("Before");
	}

	@AfterEach // 类似于junit4的@After
	public void after() {
		System.out.println("After");
	}

	@Test
	void test()  {
		System.out.println(123);
	}


}
