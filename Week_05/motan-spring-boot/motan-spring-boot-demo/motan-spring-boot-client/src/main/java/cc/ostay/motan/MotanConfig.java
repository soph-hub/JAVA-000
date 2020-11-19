package cc.ostay.motan;

import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Motan 通用配置
 *
 */
@Configuration
public class MotanConfig {

	/**
	 * 声明 Annotation 用来指定需要解析的包名
	 * 
	 * @return AnnotationBean
	 */
	@Bean
	@ConfigurationProperties(prefix = "motan.annotation")
	public static AnnotationBean motanAnnotationBean() {
		return new AnnotationBean();
	}

	/**
	 * Motan协议配置
	 * 
	 * @return ProtocolConfigBean
	 */
	@Bean(name = "motan")
	@ConfigurationProperties(prefix = "motan.protocol")
	public ProtocolConfigBean protocolConfig() {
		return new ProtocolConfigBean();
	}

	/**
	 * Motan注册中心配置
	 * 
	 * @return RegistryConfigBean
	 */
	@Bean(name = "registryConfig")
	@ConfigurationProperties(prefix = "motan.registry")
	public RegistryConfigBean registryConfig() {
		return new RegistryConfigBean();
	}
}
