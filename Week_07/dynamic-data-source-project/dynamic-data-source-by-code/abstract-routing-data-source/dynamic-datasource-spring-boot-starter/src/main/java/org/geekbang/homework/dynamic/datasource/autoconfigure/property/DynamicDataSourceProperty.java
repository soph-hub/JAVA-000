package org.geekbang.homework.dynamic.datasource.autoconfigure.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.geekbang.homework.dynamic.datasource.strategy.DynamicDataSourceStrategy;
import org.geekbang.homework.dynamic.datasource.strategy.LoadBalanceDynamicDataSourceStrategy;
import org.geekbang.homework.dynamic.datasource.toolkit.CryptoUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = DynamicDataSourceProperty.PREFIX)
public class DynamicDataSourceProperty {

    public static final String PREFIX = "spring.dynamic-datasource";
    public static final String HEALTH = PREFIX + ".health";

    /**
     * 必须设置默认的库,默认master
     */
    private String primary = "master";
    /**
     * 是否启用严格模式,默认不启动. 严格模式下未匹配到数据源直接报错, 非严格模式下则使用默认数据源primary所设置的数据源
     */
    private Boolean strict = false;
    /**
     * 是否使用 spring actuator 监控检查，默认不检查
     */
    private boolean health = false;

    /**
     * 多数据源选择算法clazz，默认负载均衡算法
     */
    private Class<? extends DynamicDataSourceStrategy> strategy = LoadBalanceDynamicDataSourceStrategy.class;
    /**
     * aop切面顺序，默认优先级最高
     */
    private Integer order = Ordered.HIGHEST_PRECEDENCE;
    /**
     * 全局默认publicKey
     */
    private String publicKey = CryptoUtils.DEFAULT_PUBLIC_KEY_STRING;
    /**
     * aop 切面是否只允许切 public 方法
     */
    private boolean allowedPublicOnly = true;
}