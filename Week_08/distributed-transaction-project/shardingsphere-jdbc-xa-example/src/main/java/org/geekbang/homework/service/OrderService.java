package org.geekbang.homework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.geekbang.homework.entity.Order;

public interface OrderService extends IService<Order> {
    TransactionType xaSave(Order entity);

    void xaSaveFailed(Order entity);
}
