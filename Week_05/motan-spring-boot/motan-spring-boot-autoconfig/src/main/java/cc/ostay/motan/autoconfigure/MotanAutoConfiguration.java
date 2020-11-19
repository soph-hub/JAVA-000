package cc.ostay.motan.autoconfigure;

import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;
import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * motan 自动配置
 */
@Configuration
public class MotanAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "spring.motan.annotation.package")
    @ConfigurationProperties(prefix = "spring.motan.annotation")
    public static AnnotationBean annotationBean() {
        return new AnnotationBean();
    }

    @Bean(name = "motan")
    @ConditionalOnProperty(name = "spring.motan.protocol.name")
    @ConfigurationProperties(prefix = "spring.motan.protocol", ignoreUnknownFields = false)
    public ProtocolConfigBean protocolConfigBean() {
        return new ProtocolConfigBean();
    }

    @Bean(name = "registry")
    @ConditionalOnProperty(name = "spring.motan.registry.reg-protocol")
    @ConfigurationProperties(prefix = "spring.motan.registry", ignoreUnknownFields = false)
    public RegistryConfigBean registryConfigBean() {
        return new RegistryConfigBean();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.motan.server.registry")
    @ConditionalOnBean({ProtocolConfigBean.class, RegistryConfigBean.class})
    @ConfigurationProperties(prefix = "spring.motan.server", ignoreUnknownFields = false)
    public BasicServiceConfigBean basicServiceConfigBean() {
        return new BasicServiceConfigBean();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.motan.client.registry")
    @ConditionalOnBean({ProtocolConfigBean.class, RegistryConfigBean.class})
    @ConfigurationProperties(prefix = "spring.motan.client", ignoreUnknownFields = false)
    public BasicRefererConfigBean basicRefererConfigBean() {
        return new BasicRefererConfigBean();
    }
}
