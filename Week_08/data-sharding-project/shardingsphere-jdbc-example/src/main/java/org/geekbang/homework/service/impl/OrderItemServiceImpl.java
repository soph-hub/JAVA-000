package org.geekbang.homework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.homework.entity.OrderItem;
import org.geekbang.homework.mapper.OrderItemMapper;
import org.geekbang.homework.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
