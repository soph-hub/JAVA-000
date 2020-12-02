package org.geekbang.homework.dynamic.datasource;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import lombok.Data;
import org.geekbang.homework.dynamic.datasource.strategy.DynamicDataSourceStrategy;

@Data
public class GroupDataSource {

    private String groupName;

    private DynamicDataSourceStrategy dynamicDataSourceStrategy;

    private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public GroupDataSource(String groupName, DynamicDataSourceStrategy dynamicDataSourceStrategy) {
        this.groupName = groupName;
        this.dynamicDataSourceStrategy = dynamicDataSourceStrategy;
    }

    /**
     * add a new datasource to this group
     *
     * @param ds         the name of the datasource
     * @param dataSource datasource
     */
    public DataSource addDatasource(String ds, DataSource dataSource) {
        return dataSourceMap.put(ds, dataSource);
    }

    /**
     * @param ds the name of the datasource
     */
    public DataSource removeDatasource(String ds) {
        return dataSourceMap.remove(ds);
    }

    public String determineDataSource() {
        return dynamicDataSourceStrategy.determineDataSource(new ArrayList<>(dataSourceMap.keySet()));
    }

    public int size() {
        return dataSourceMap.size();
    }
}