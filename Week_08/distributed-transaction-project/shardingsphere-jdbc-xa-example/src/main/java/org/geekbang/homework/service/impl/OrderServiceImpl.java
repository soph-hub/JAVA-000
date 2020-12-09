package org.geekbang.homework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.sql.SQLException;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.geekbang.homework.entity.Order;
import org.geekbang.homework.entity.OrderItem;
import org.geekbang.homework.mapper.OrderMapper;
import org.geekbang.homework.service.OrderItemService;
import org.geekbang.homework.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    OrderItemService orderItemService;

    @Override
    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public TransactionType xaSave(Order entity) {
        save(entity);
        OrderItem item = new OrderItem();
        item.setOrderId(entity.getOrderId());
        item.setUserId(entity.getUserId());
        item.setStatus(entity.getStatus());
        return orderItemService.xaSave(item);
    }

    @Override
    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void xaSaveFailed(Order entity) {
        save(entity);
        OrderItem item = new OrderItem();
        item.setOrderId(entity.getOrderId());
        item.setUserId(entity.getUserId());
        item.setStatus(entity.getStatus());
        orderItemService.xaSaveFailed(item);
    }
}
