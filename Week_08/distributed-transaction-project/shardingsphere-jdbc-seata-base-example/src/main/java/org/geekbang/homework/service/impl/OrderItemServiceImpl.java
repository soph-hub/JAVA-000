package org.geekbang.homework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.geekbang.homework.entity.Order;
import org.geekbang.homework.entity.OrderItem;
import org.geekbang.homework.mapper.OrderItemMapper;
import org.geekbang.homework.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
    @Override
    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public TransactionType xaSave(OrderItem entity) {
        save(entity);
        return TransactionTypeHolder.get();
    }

    @Override
    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void xaSaveFailed(OrderItem entity) {
        save(entity);
        throw new RuntimeException("mock transaction failed");
    }
}
