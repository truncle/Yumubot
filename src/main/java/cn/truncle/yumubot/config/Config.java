package cn.truncle.yumubot.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 将配置信息映射成实体类。
 */
@Component
@ConfigurationProperties(prefix = "yumubot.config")
@Getter
public class Config {

    @Getter
    public static class Ppy {
        private Integer appId;
        private String callbackUrl;
        private String token;
        private String baseUrl;
    }

    private Ppy ppy;
}