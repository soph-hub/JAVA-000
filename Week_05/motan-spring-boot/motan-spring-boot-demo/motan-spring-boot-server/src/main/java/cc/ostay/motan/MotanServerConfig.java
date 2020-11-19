package cc.ostay.motan;

import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Motan Server配置
 *
 */
@Configuration
public class MotanServerConfig {

	/**
	 * Motan 服务端配置
	 * 
	 * @return BasicServiceConfigBean
	 */
	@Bean
	@ConfigurationProperties(prefix = "motan.server")
	public BasicServiceConfigBean baseServiceConfig() {
		return new BasicServiceConfigBean();
	}
}
