package cn.truncle.yumubot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    public void handleEvent(Object event){
        logger.debug("这里是notice service");
    }
}
