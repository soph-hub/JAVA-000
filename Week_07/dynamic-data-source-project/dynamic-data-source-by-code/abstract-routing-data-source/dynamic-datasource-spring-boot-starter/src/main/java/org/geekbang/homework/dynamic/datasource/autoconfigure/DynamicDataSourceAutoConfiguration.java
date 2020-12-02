package org.geekbang.homework.dynamic.datasource.autoconfigure;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.geekbang.homework.dynamic.datasource.DynamicRoutingDataSource;
import org.geekbang.homework.dynamic.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import org.geekbang.homework.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import org.geekbang.homework.dynamic.datasource.autoconfigure.property.DynamicDataSourceProperty;
import org.geekbang.homework.dynamic.datasource.provider.DynamicDataSourceProvider;
import org.geekbang.homework.dynamic.datasource.toolkit.DataSourceUtil;
import org.geekbang.homework.dynamic.datasource.toolkit.InlineExpressionParser;
import org.geekbang.homework.dynamic.datasource.toolkit.PropertyUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.lang.NonNull;

@Configuration
@EnableConfigurationProperties({DynamicDataSourceProperty.class})
@ConditionalOnProperty(prefix = DynamicDataSourceProperty.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@RequiredArgsConstructor
public class DynamicDataSourceAutoConfiguration implements EnvironmentAware {

    private final DynamicDataSourceProperty properties;

    private final Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new DynamicDataSourceProvider(dataSourceMap);
    }

    @Bean("dynamicDataSource")
    @ConditionalOnMissingBean
    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setPrimary(properties.getPrimary());
        dataSource.setStrict(properties.getStrict());
        dataSource.setStrategy(properties.getStrategy());
        dataSource.setProvider(dynamicDataSourceProvider);
        return dataSource;
    }

    @Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        DynamicDataSourceAnnotationInterceptor interceptor = new DynamicDataSourceAnnotationInterceptor(properties.isAllowedPublicOnly());
        DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(interceptor);
        advisor.setOrder(properties.getOrder());
        return advisor;
    }

    @Override
    public final void setEnvironment(@NonNull final Environment environment) {
        String prefix = DynamicDataSourceProperty.PREFIX + ".";
        for (String each : getDataSourceNames(environment, prefix)) {
            try {
                dataSourceMap.put(each, getDataSource(environment, prefix, each));
            } catch (final ReflectiveOperationException ex) {
                throw new RuntimeException("Can't find datasource type!", ex);
            }
        }
    }

    private List<String> getDataSourceNames(final Environment environment, final String prefix) {
        StandardEnvironment standardEnv = (StandardEnvironment) environment;
        standardEnv.setIgnoreUnresolvableNestedPlaceholders(true);
        return null == standardEnv.getProperty(prefix + "name")
            ? new InlineExpressionParser(standardEnv.getProperty(prefix + "names")).splitAndEvaluate() : Collections.singletonList(standardEnv.getProperty(prefix + "name"));
    }

    @SuppressWarnings("unchecked")
    private DataSource getDataSource(final Environment environment, final String prefix, final String dataSourceName) throws ReflectiveOperationException {
        Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + dataSourceName.trim(), Map.class);
        Preconditions.checkState(!dataSourceProps.isEmpty(), "Wrong datasource properties!");
        return DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);
    }
    
}