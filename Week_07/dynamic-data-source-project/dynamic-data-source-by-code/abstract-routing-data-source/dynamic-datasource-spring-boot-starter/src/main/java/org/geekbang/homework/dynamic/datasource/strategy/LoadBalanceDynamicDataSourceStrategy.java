package org.geekbang.homework.dynamic.datasource.strategy;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;

public class LoadBalanceDynamicDataSourceStrategy implements DynamicDataSourceStrategy {

    /**
     * 负载均衡计数器
     */
    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public String determineDataSource(List<String> dataSources) {
        return dataSources.get(Math.abs(index.getAndAdd(1) % dataSources.size()));
    }
}