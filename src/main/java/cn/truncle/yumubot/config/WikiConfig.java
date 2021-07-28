package cn.truncle.yumubot.config;

import cn.truncle.yumubot.util.WikiUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WikiConfig {
    @Bean
    public WikiUtil wikiUtil(){
        return new WikiUtil();
    }
}
