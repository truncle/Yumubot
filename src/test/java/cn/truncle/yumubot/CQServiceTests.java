package cn.truncle.yumubot;

import cn.truncle.yumubot.entity.CQObject;
import cn.truncle.yumubot.service.CQService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class CQServiceTests {
    private static final Logger logger = LoggerFactory.getLogger(OSUServiceTests.class);

    @Autowired
    private CQService cqService;

    @Test
    void sendPrivateMsgTest(){
        Assertions.assertNotNull(cqService.sendPrivateMsg("365246692", "14218879"));
    }

    @Test
    void imgTest(){
        File a = new File("D:/Pictures/photo.jpg");
        CQObject cq = CQObject.parseImg("https://images.dog.ceo/breeds/mix/cookie.jpg");
        System.out.println(cq);
        cqService.sendPrivateMsg("365246692", "nimasl"+cq);
        Assertions.assertNotNull(cqService.getImage("bf961a9805392e190e9e97432e2e206d.image"));
    }
    @Test
    void kick(){
        Assertions.assertFalse(cqService.setGroupKick("521412535","2207081889"));
    }


}
