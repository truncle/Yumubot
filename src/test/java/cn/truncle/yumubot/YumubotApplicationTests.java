package cn.truncle.yumubot;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.truncle.yumubot.service.OSUService;

@SpringBootTest
class YumubotApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(YumubotApplicationTests.class);

    @Autowired
    private OSUService osuService;

    @Test
    void contextLoads() {
    }

}
