package org.geekbang.homework.dynamic.datasource.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.geekbang.homework.dynamic.datasource.support.DataSourceClassResolver;
import org.geekbang.homework.dynamic.datasource.toolkit.DataSourceContextHolder;
import org.springframework.lang.NonNull;

public class DynamicDataSourceAnnotationInterceptor implements MethodInterceptor {

    private final DataSourceClassResolver dataSourceClassResolver;

    public DynamicDataSourceAnnotationInterceptor(Boolean allowedPublicOnly) {
        dataSourceClassResolver = new DataSourceClassResolver(allowedPublicOnly);
    }

    @Override
    public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
        try {
            String dsKey = determineDatasourceKey(invocation);
            DataSourceContextHolder.push(dsKey);
            return invocation.proceed();
        } finally {
            DataSourceContextHolder.poll();
        }
    }

    private String determineDatasourceKey(MethodInvocation invocation) {
        return dataSourceClassResolver.findDataSourceKey(invocation.getMethod(), invocation.getThis());
    }
}