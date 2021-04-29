package cn.truncle.yumubot;

import cn.truncle.yumubot.service.MessageService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageServiceTests {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceTests.class);

    @Autowired
    private MessageService messageService;



}
