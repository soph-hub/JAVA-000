package cc.ostay.controller;

import cc.ostay.service.HelloService;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Motan 测试
 * 
 */
@RestController
public class HelloController {

	@MotanReferer(basicReferer = "clientBasicConfig")
	private HelloService helloService;

	/**
	 * Motan测试
	 * 
	 * @param name Name
	 * @return String
	 */
	@RequestMapping(value = "/{name}")
	@ResponseBody
	public String hello(@PathVariable String name) {
		return helloService.sayHello(name);
	}

}
