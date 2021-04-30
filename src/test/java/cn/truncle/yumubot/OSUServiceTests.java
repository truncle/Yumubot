package cn.truncle.yumubot;

import cn.truncle.yumubot.service.OSUService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OSUServiceTests {
    private static final Logger logger = LoggerFactory.getLogger(OSUServiceTests.class);

    @Autowired
    private OSUService osuService;

    @Test
    void getClientCredentialsTest(){
        Assertions.assertDoesNotThrow(()->osuService.getClientCredentials());
        Assertions.assertNotNull(osuService.getToken());
        logger.debug("token = " + osuService.getToken());
    }

    @Test
    void getUserTest(){
        Assertions.assertNotNull(osuService.getUser("14218879"));
    }

    @Test
    void getBeatMapTest(){
        Assertions.assertNotNull(osuService.getBeatMap("2828902"));
    }

    @Test
    void getUserBeatmapScoreTest(){
        Assertions.assertNotNull(osuService.getUserBeatmapScore("1642274", "14218879"));
    }
}
