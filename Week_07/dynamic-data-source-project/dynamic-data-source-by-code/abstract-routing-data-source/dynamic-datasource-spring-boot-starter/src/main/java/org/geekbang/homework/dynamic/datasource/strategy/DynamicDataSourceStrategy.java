package org.geekbang.homework.dynamic.datasource.strategy;

import java.util.List;
import javax.sql.DataSource;

public interface DynamicDataSourceStrategy {

    /**
     * determine a database from the given dataSources
     *
     * @param dataSources given dataSources
     * @return final dataSource
     */
    String determineDataSource(List<String> dataSources);
}