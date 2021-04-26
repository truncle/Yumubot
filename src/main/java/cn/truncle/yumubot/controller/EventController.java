package cn.truncle.yumubot.controller;

import cn.truncle.yumubot.service.MessageService;
import cn.truncle.yumubot.service.NoticeService;
import cn.truncle.yumubot.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private RequestService requestService;

    /**
     * 把事件分发到对应的服务进行处理
     */
    @PostMapping("/event")
    public Object responseEvent(@RequestBody Map event){
        switch((String)event.get("post_type")){
            case "message":
                messageService.handleMessage(event); break;
            case "notice":noticeService.handleEvent(event); break;
            case "request":requestService.handleEvent(event); break;
            default:
                logger.debug("Heartbeat");
        }
        return null;
    }
}
