package org.geekbang.homework;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.geekbang.homework.entity.Address;
import org.geekbang.homework.entity.Order;
import org.geekbang.homework.entity.OrderItem;
import org.geekbang.homework.entity.User;
import org.geekbang.homework.service.AddressService;
import org.geekbang.homework.service.OrderItemService;
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
	@Autowired
	OrderItemService orderItemService;
	@Test
	void contextLoads() {
	}

	@Test
	void initData(){
		List<User> users = Lists.newArrayList();
		for (int i = 1; i <= 100; i++) {
			users.add(new User().setUserName("用户_" + i).setUserNamePlain("用户_" + i).setPwd("123456").setAssistedQueryPwd("123456"));
		}
		userService.saveBatch(users);

		List<Address> addresses = Lists.newArrayList();
		for (int i = 1; i <= 100; i++) {
			Address address = new Address();
			address.setAddressId((long) i);
			address.setAddressName("address_" + i);
			addresses.add(address);
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
			OrderItem item = new OrderItem();
			item.setOrderId(order.getOrderId());
			item.setUserId(userId);
			item.setStatus("INSERT_TEST");
			orderItemService.save(item);
		}
	}

}
