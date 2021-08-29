package cn.truncle.yumubot.controller;

import cn.truncle.yumubot.model.Event;
import cn.truncle.yumubot.util.Instruction;
import cn.truncle.yumubot.service.MessageService;
import cn.truncle.yumubot.service.NoticeService;
import cn.truncle.yumubot.service.RequestService;
import cn.truncle.yumubot.util.InsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

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
    public Object responseEvent(@RequestBody Event event){
        Class<?> clz;
        Method method;
        Instruction ins;
        //TODO 返回response用于快速回复
        try {
            switch (event.getPostType()) {
                case "message":
                    clz = messageService.getClass();
                    ins = Instruction.valueOf(InsUtil.getIns(event.getRawMessage()));
                    method = clz.getMethod(ins.getMethod(), Event.class);
                    method.invoke(messageService, event);
                    break;
                case "notice":
                    clz = noticeService.getClass();
                    ins = Instruction.valueOf(InsUtil.getIns(event.getRawMessage()));
                    method = clz.getMethod(ins.getMethod(), Event.class);
                    method.invoke(noticeService, event);
                    break;
                case "request":
                    clz =requestService.getClass();
                    ins = Instruction.valueOf(InsUtil.getIns(event.getRawMessage()));
                    method = clz.getMethod(ins.getMethod(), Event.class);
                    method.invoke(requestService, event);
                    break;
                default:
                    logger.debug("Heartbeat");
            }
        }catch (Exception e){
            logger.error("Something wrong");
        }
        return null;
    }
}
