package org.geekbang.homework.dynamic.datasource.strategy;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDynamicDataSourceStrategy implements DynamicDataSourceStrategy {

    @Override
    public String determineDataSource(List<String> dataSources) {
        return dataSources.get(ThreadLocalRandom.current().nextInt(dataSources.size()));
    }
}