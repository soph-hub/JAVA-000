package org.geekbang.homework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.geekbang.homework.entity.Order;
import org.geekbang.homework.entity.OrderItem;

public interface OrderItemService extends IService<OrderItem> {
    TransactionType xaSave(OrderItem entity);

    void xaSaveFailed(OrderItem entity);
}
