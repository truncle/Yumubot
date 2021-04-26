package cn.truncle.yumubot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);
    public void handleEvent(Object event){
        logger.debug("这里是request service");
    }
}
