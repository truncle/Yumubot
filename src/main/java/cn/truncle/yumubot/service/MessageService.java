package cn.truncle.yumubot.service;

import cn.truncle.yumubot.model.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private OSUService osuService;

    @Autowired
    private CQService cqService;

    public void getInfo(Event event){
        //TODO 参数解析、校验。调用其它service完成指令对应功能
        cqService.sendPrivateMsg(event.getUserId(), "你好");
    }

}
