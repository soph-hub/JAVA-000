package org.geekbang.homework.dynamic.datasource.provider;

import java.util.Map;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class DynamicDataSourceProvider {

    /**
     * 所有数据源
     */
    private final Map<String, DataSource> dataSourceMap;

    /**
     * 加载所有数据源
     *
     * @return 所有数据源，key为数据源名称
     */
    public Map<String, DataSource> loadDataSources() {
        return dataSourceMap;
    }

}