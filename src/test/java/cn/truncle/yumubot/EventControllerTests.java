package cn.truncle.yumubot;

import cn.truncle.yumubot.controller.EventController;
import cn.truncle.yumubot.service.CQService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventControllerTests {
    private static final Logger logger = LoggerFactory.getLogger(EventControllerTests.class);
    @Autowired
    private EventController eventController;

    @Test
    public void test(){

    }
}
