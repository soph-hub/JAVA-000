package org.geekbang.homework;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.geekbang.homework.entity.Address;
import org.geekbang.homework.entity.Order;
import org.geekbang.homework.entity.User;
import org.geekbang.homework.service.AddressService;
import org.geekbang.homework.service.OrderService;
import org.geekbang.homework.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShardingSphereJdbcExampleApplicationTests {

	@Autowired
	UserService userService;
	@Autowired
	AddressService addressService;
	@Autowired
	OrderService orderService;

	@Test
	void contextLoads() {
	}

	@Test
	void initData(){
		List<User> users = Lists.newArrayList();
		for (int i = 1; i <= 100; i++) {
			users.add(new User().setUserName("user_" + i).setUserNamePlain("user_" + i).setPwd("123456").setAssistedQueryPwd("123456"));
		}
		userService.saveBatch(users);

		List<Address> addresses = Lists.newArrayList();
		for (int i = 1; i <= 100; i++) {
			addresses.add(new Address().setAddressName("address_" + i));
		}
		addressService.saveBatch(addresses);
	}

	@Test
	void insertOrder()  {
		System.out.println("---------------------------- Insert Data ----------------------------");
		List<User> users = userService.list();
		List<Long> userIds = users.stream().map(User::getUserId).collect(Collectors.toList());

		List<Address> addresses = addressService.list();
		List<Long> addressIds = addresses.stream().map(Address::getAddressId).collect(Collectors.toList());

		for (int i = 1; i <= 100; i++) {
			Long userId = userIds.get(new Random().nextInt(userIds.size()));
			Order order = new Order();
			order.setUserId(userId);
			order.setAddressId(addressIds.get(new Random().nextInt(addressIds.size())));
			order.setStatus("INSERT_TEST");
			orderService.save(order);
		}
	}

	@Test
	void xaSave()  {
		System.out.println("---------------------------- Insert Data ----------------------------");
		List<User> users = userService.list();
		List<Long> userIds = users.stream().map(User::getUserId).collect(Collectors.toList());

		List<Address> addresses = addressService.list();
		List<Long> addressIds = addresses.stream().map(Address::getAddressId).collect(Collectors.toList());

		Long userId = userIds.get(new Random().nextInt(userIds.size()));
		Order order = new Order();
		order.setUserId(userId);
		order.setAddressId(addressIds.get(new Random().nextInt(addressIds.size())));
		order.setStatus("INSERT_TEST");
		System.out.println(orderService.xaSave(order));
	}

	@Test

	void xaSaveFailed()  {
		System.out.println("---------------------------- Insert Data ----------------------------");
		List<User> users = userService.list();
		List<Long> userIds = users.stream().map(User::getUserId).collect(Collectors.toList());

		List<Address> addresses = addressService.list();
		List<Long> addressIds = addresses.stream().map(Address::getAddressId).collect(Collectors.toList());

		Long userId = userIds.get(new Random().nextInt(userIds.size()));
		Order order = new Order();
		order.setUserId(userId);
		order.setAddressId(addressIds.get(new Random().nextInt(addressIds.size())));
		order.setStatus("INSERT_TEST");
		orderService.xaSaveFailed(order);
	}

}
