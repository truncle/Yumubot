package cn.truncle.yumubot;

import cn.truncle.yumubot.controller.EventController;
import cn.truncle.yumubot.service.OSUService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YumubotApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(YumubotApplicationTests.class);

    @Autowired
    private OSUService osuService;

    @Test
    void contextLoads() {
    }

}
