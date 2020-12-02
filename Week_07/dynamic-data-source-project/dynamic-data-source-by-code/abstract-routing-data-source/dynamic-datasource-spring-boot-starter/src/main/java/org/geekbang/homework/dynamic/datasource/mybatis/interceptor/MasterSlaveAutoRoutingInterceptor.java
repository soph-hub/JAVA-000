package org.geekbang.homework.dynamic.datasource.mybatis.interceptor;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.util.Strings;
import org.geekbang.homework.dynamic.datasource.autoconfigure.property.DynamicDataSourceProperty;
import org.geekbang.homework.dynamic.datasource.support.DataSourceHealthIndicator;
import org.geekbang.homework.dynamic.datasource.toolkit.DataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.geekbang.homework.dynamic.datasource.constant.DataSourceConstants.DataSourceGroup;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Slf4j
public class MasterSlaveAutoRoutingInterceptor implements Interceptor {

    @Autowired
    private DynamicDataSourceProperty properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
        if(!synchronizationActive) {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            boolean empty = true;
            try {
                empty = Strings.isEmpty(DataSourceContextHolder.peek());
                if (empty) {
                    DataSourceContextHolder.push(getDataSource(ms));
                }
                return invocation.proceed();
            } finally {
                if (empty) {
                    DataSourceContextHolder.clear();
                }
            }
        }
        return invocation.proceed();
    }

    /**
     * 获取动态数据源名称，重写注入 DataSourceHealthIndicator 支持数据源健康状况判断选择
     *
     * @param mappedStatement mybatis MappedStatement
     * @return 获取真实的数据源名称
     */
    public String getDataSource(MappedStatement mappedStatement) {
        String slave = DataSourceGroup.SLAVE;
        if (properties.isHealth()) {
            /*
             * 根据从库健康状况，判断是否切到主库
             */
            boolean health = DataSourceHealthIndicator.getHealth(DataSourceGroup.SLAVE);
            if (!health) {
                health = DataSourceHealthIndicator.getHealth(DataSourceGroup.MASTER);
                if (health) {
                    slave = DataSourceGroup.MASTER;
                }
            }
        }
        return SqlCommandType.SELECT == mappedStatement.getSqlCommandType() ? slave : DataSourceGroup.MASTER;
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof Executor ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}