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

}
