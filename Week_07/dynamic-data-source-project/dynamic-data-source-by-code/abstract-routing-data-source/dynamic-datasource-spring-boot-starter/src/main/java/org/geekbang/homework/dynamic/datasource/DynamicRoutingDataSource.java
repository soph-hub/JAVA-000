package org.geekbang.homework.dynamic.datasource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.geekbang.homework.dynamic.datasource.provider.DynamicDataSourceProvider;
import org.geekbang.homework.dynamic.datasource.strategy.DynamicDataSourceStrategy;
import org.geekbang.homework.dynamic.datasource.toolkit.DataSourceContextHolder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean, DisposableBean {

    // 数据源名称包含下划线，则会把下划线分割的第一部分作为数据源组名
    private static final String LINE_THROUGH = "-";
    // 用于生成真实数据源的map
    @Setter
    private DynamicDataSourceProvider provider;
    // 当同一个分组有多个数据源时，采用的负载均衡算法，目前支持轮询和随机访问两种，分别是LoadBalanceDynamicDataSourceStrategy和RandomDynamicDataSourceStrategy类
    @Setter
    private Class<? extends DynamicDataSourceStrategy> strategy;
    // 默认数据源的名称或分组名称
    @Setter
    private String primary;
    // 是否保持粘性，即访问了某个数据源，接下来就一直访问那个数据源
    @Setter
    private boolean strict;

    /**
     * 所有数据库
     */
    private final Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
    /**
     * 分组数据库
     */
    private final Map<String, GroupDataSource> groupDataSources = new ConcurrentHashMap<>();
    
    /**
     * 获取当前所有的数据源
     *
     * @return 当前所有数据源
     */
    public Map<String, DataSource> getCurrentDataSources() {
        return dataSourceMap;
    }

    /**
     * 获取的当前所有的分组数据源
     *
     * @return 当前所有的分组数据源
     */
    public Map<String, GroupDataSource> getCurrentGroupDataSources() {
        return groupDataSources;
    }

    // 该类继承了spring的AbstractRoutingDataSource类，所以需要实现它的抽象方法，选择数据源。这里需要关注的是DynamicDataSourceContextHolder类
    @Override
    protected Object determineCurrentLookupKey() {
        return determineCurrentLookupKey(DataSourceContextHolder.peek());
    }

    /**
     * 获取数据源
     *
     * @param ds 数据源名称
     * @return 数据源
     */
    public String determineCurrentLookupKey(String ds) {
        if (Strings.isEmpty(ds)) {
            // 如果没有指定数据源，则调用determinePrimaryDataSource方法来选择默认数据源
            return determinePrimaryDataSourceName();
        } else if (!groupDataSources.isEmpty() && groupDataSources.containsKey(ds)) {
            // 如果有数据源分组，则判断指定的是否是数据源分组，例如配置了数据源slave-1,slave-2，这个时候，指定使用的数据源是slave，则会先选择slave分组，再从分组里选择一个数据源
            log.debug("dynamic-datasource switch to the datasource named [{}]", ds);
            return groupDataSources.get(ds).determineDataSource();
        } else if (dataSourceMap.containsKey(ds)) {
            // 如果没有对应的数据源分组，则直接根据数据源名称来获取数据源
            log.debug("dynamic-datasource switch to the datasource named [{}]", ds);
            return ds;
        }
        // 找不到指定数据源，就没办法实现粘性访问
        if (strict) {
            throw new RuntimeException("dynamic-datasource could not find a datasource named" + ds);
        }
        // 如果指定的数据源没有找到，则用默认数据源
        return determinePrimaryDataSourceName();
    }

    // 没有指定数据源的时候，就使用默认数据源
    private String determinePrimaryDataSourceName() {
        log.debug("dynamic-datasource switch to the primary datasource");
        return groupDataSources.containsKey(primary)
            ? groupDataSources.get(primary).determineDataSource()
            : primary;
    }

    // addDataSource和closeDataSource两个方法，就是实现动态增删数据源的基础
    /**
     * 添加数据源
     *
     * @param dataSourceName         数据源名称
     * @param dataSource 数据源
     */
    public synchronized void addDataSource(String dataSourceName, DataSource dataSource) {
        DataSource oldDataSource = dataSourceMap.put(dataSourceName, dataSource);
        // 新数据源添加到分组
        this.addGroupDataSource(dataSourceName, dataSource);
        // 关闭老的数据源
        if (oldDataSource != null) {
            try {
                closeDataSource(oldDataSource);
            } catch (Exception e) {
                log.error("dynamic-datasource - remove the database named [{}]  failed", dataSourceName, e);
            }
        }
        log.info("dynamic-datasource - load a datasource named [{}] success", dataSourceName);
    }

    /**
     * 新数据源添加到分组
     *
     * @param dataSourceName         新数据源的名字
     * @param dataSource 新数据源
     */
    private void addGroupDataSource(String dataSourceName, DataSource dataSource) {
        if (dataSourceName.contains(LINE_THROUGH)) {
            String group = dataSourceName.split(LINE_THROUGH)[0];
            GroupDataSource groupDataSource = groupDataSources.get(group);
            if (groupDataSource == null) {
                try {
                    groupDataSource = new GroupDataSource(group, strategy.getDeclaredConstructor().newInstance());
                    groupDataSources.put(group, groupDataSource);
                } catch (Exception e) {
                    throw new RuntimeException("dynamic-datasource - add the datasource named " + dataSourceName + " error", e);
                }
            }
            groupDataSource.addDatasource(dataSourceName, dataSource);
        }
    }
    /**
     * 关闭数据源
     *
     * @param dataSource 数据源
     */
    public synchronized void closeDataSource(DataSource dataSource) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (dataSource instanceof ItemDataSource) {
            ((ItemDataSource) dataSource).close();
        } else {
            Class<? extends DataSource> clazz = dataSource.getClass();
            Method closeMethod = clazz.getDeclaredMethod("close");
            closeMethod.invoke(dataSource);
        }
    }

    // 完成初始化之后，需要对默认数据源做一个校验，如果不包含默认数据源，则直接报错
    @Override
    public void afterPropertiesSet() {
        Map<String, DataSource> dataSources = provider.loadDataSources();
        //添加并分组数据源
        for (Map.Entry<String, DataSource> dsItem : dataSources.entrySet()) {
            addDataSource(dsItem.getKey(), dsItem.getValue());
        }
        //检测默认数据源设置
        if (groupDataSources.containsKey(primary)) {
            setDefaultTargetDataSource(groupDataSources.get(primary));
            log.info("dynamic-datasource initial loaded [{}] datasource,primary group datasource named [{}]", dataSources.size(), primary);
        } else if (dataSourceMap.containsKey(primary)) {
            setDefaultTargetDataSource(groupDataSources.get(primary));
            log.info("dynamic-datasource initial loaded [{}] datasource,primary datasource named [{}]", dataSources.size(), primary);
        } else {
            throw new RuntimeException("dynamic-datasource Please check the setting of primary");
        }
        Map<Object, Object> targetDataSources = dataSources.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    // 销毁bean的时候，需要调用所有真实数据源的close方法，关闭数据源
    @Override
    public void destroy() throws Exception {
        log.info("dynamic-datasource start closing ....");
        for (Map.Entry<String, DataSource> item : dataSourceMap.entrySet()) {
            DataSource dataSource = item.getValue();
            Class<? extends DataSource> clazz = dataSource.getClass();
            try {
                Method closeMethod = clazz.getDeclaredMethod("close");
                closeMethod.invoke(dataSource);
            } catch (NoSuchMethodException e) {
                log.warn("dynamic-datasource close the datasource named [{}] failed,", item.getKey());
            }
        }
        log.info("dynamic-datasource all closed success,bye");
    }

}