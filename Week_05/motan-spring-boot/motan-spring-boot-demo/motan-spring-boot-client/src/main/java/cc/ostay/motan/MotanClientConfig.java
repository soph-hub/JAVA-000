package cc.ostay.motan;

import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Motan Client 配置
 *
 */
@Configuration
public class MotanClientConfig {

	/**
	 * 客户端配置
	 * 
	 * @return BasicRefererConfigBean
	 */
	@Bean(name = "clientBasicConfig")
	@ConfigurationProperties(prefix = "motan.client")
	public BasicRefererConfigBean baseRefererConfig() {
		return new BasicRefererConfigBean();
	}
}
