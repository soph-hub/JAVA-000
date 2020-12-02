package org.geekbang.homework;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.util.Lists;
import org.geekbang.homework.entity.User;
import org.geekbang.homework.service.read.UserReadService;
import org.geekbang.homework.service.write.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AbstractRoutingDataSourceExampleApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	UserReadService userReadService;

	@Test
	void contextLoads() {
	}

	@Test
	void testSave(){
		List<User> users = Lists.list(
			new User().setName("铠").setGender(1).setBirthday(LocalDate.of(1998, 1, 1)).setAge(23),
			new User().setName("澜").setGender(1).setBirthday(LocalDate.of(1998, 2, 1)).setAge(23),
			new User().setName("李信").setGender(1).setBirthday(LocalDate.of(1998, 3, 1)).setAge(23),
			new User().setName("韩信").setGender(1).setBirthday(LocalDate.of(1998, 4, 1)).setAge(23)
		);
		userService.insertBatch(users);
	}

	@Test
	void test(){
		System.out.println(userReadService.getById(1333646426283151361L));

		List<Long> ids = Lists.list(1333646424316022785L,1333646426283151361L,1333646426283151362L);
		System.out.println(userReadService.getByIds(ids));

		userService.updateById(new User().setId(1333646426283151364L).setBirthday(LocalDate.of(1999, 5, 1)));

		System.out.println(userReadService.list(new LambdaQueryWrapper<User>().eq(User::getId, 1333646426283151364L)));
	}
}
