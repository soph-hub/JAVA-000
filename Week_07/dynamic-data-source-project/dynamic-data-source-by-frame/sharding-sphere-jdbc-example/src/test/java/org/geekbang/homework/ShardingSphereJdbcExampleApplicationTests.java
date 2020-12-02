package org.geekbang.homework;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.util.Lists;
import org.geekbang.homework.entity.User;
import org.geekbang.homework.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShardingSphereJdbcExampleApplicationTests {

	@Autowired
	UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	void testSave(){
		List<User> users = Lists.newArrayList(
			new User().setName("瑶妹").setGender(0).setBirthday(LocalDate.of(2003, 1, 1)).setAge(18),
			new User().setName("曜").setGender(1).setBirthday(LocalDate.of(2002, 2, 1)).setAge(19),
			new User().setName("镜").setGender(0).setBirthday(LocalDate.of(2001, 3, 1)).setAge(20),
			new User().setName("云中君").setGender(1).setBirthday(LocalDate.of(2000, 4, 1)).setAge(21),
			new User().setName("阿骨朵").setGender(0).setBirthday(LocalDate.of(1999, 5, 1)).setAge(22)
		);
		userService.saveBatch(users);
	}

	@Test
	void test(){
		System.out.println(userService.getById(1333646426283151361L));

		List<Long> ids = Lists.list(1333646424316022785L,1333646426283151361L,1333646426283151362L);
		System.out.println(userService.listByIds(ids));

		userService.updateById(new User().setId(1333646426283151364L).setBirthday(LocalDate.of(1999, 5, 1)));

		System.out.println(userService.list(new LambdaQueryWrapper<User>().eq(User::getId, 1333646426283151364L)));


	}
}
