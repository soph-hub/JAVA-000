package cc.ostay.service.impl;

import cc.ostay.service.HelloService;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

@MotanService
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        System.out.println(name + " invoked rpc service");
        return "Hello " + name;
    }
}
