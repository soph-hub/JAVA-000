package org.geekbang.homework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.homework.entity.Order;
import org.geekbang.homework.mapper.OrderMapper;
import org.geekbang.homework.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
