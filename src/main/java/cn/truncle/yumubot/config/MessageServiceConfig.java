package cn.truncle.yumubot.config;

import cn.truncle.yumubot.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MessageServiceConfig {
    @Autowired
    ApplicationContext ac;

    @Bean("messageServices")
    public Map<String, MsgService> MessageServices(){
        ConcurrentHashMap<String, MsgService> services = new ConcurrentHashMap<>(ac.getBeansOfType(MsgService.class));
        return services;
    }
}
