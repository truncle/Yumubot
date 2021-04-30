package cn.truncle.yumubot.service;

import cn.truncle.yumubot.util.Instruction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.util.Map;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private OSUService osuService;

    @Autowired
    private CQService cqService;

    /**
     * 分别处理群聊消息和私聊消息
     */
    public void handleMessage(JSONObject message){
        System.out.println(message.toJSONString());/*
        String userId =  message.getString("user_id");
        String content = message.getString("raw_message");
        String type = message.getString("message_type");
        logger.debug("getMessage: userId = "+userId+", message = "+content);
        try{
            if(!content.startsWith("!ym")) {
                logger.debug("not instruction");
                return;
            }
            content = content.substring(3);
        }
        catch (Exception ignored){
            logger.debug("not instruction");
        }
        switch(type){
            case "private":
                onPrivateMsg(userId, content,
                        (String) message.get("sub_type"));
                break;
            case "group":onGroupMsg(userId, message.getString("group_id"),
                    content, (String) message.get("sub_type"));
            break;
            default:
                logger.debug("不认识的消息");
        }*/
    }

    /**
     * 处理私聊消息
     * @param userId qq号
     * @param content 消息内容
     * @param subType 消息子类型, 如果是好友则是 friend, 如果是群临时会话则是 group, 如果是在群中自身发送则是 group_self, 其他为 other
     */
    public void onPrivateMsg(String userId, String content, String subType){
        String response = null;
        try{
            String[] params = content.split("\\s+");
            Instruction ins = Instruction.valueOf(params[0].toUpperCase());
            switch (ins){
                case INFO:
                    response = getUserInfo(params[1]).toString();
                    break;
                case MAP:
                    response = getMapInfo(params[1]).toString();
                    break;
                case SCORE:
                    response = getUserMapScore(params[1], params[2]).toString();
                    break;
                default:
                    logger.debug("不认识的指令");
            }
            cqService.sendPrivateMsg(userId, response.substring(0,500));

        }
        catch (Exception e){
//            if(e.getCause() instanceof ConnectException)
//                response = "ppy服务器又连不上了";
            logger.debug(e.getLocalizedMessage());
        }
        logger.debug(response);
        cqService.sendPrivateMsg(userId, response);
    }

    /**
     * 处理群聊消息
     * @param userId qq号
     * @param groupId 群号
     * @param content 消息内容
     * @param subType 消息子类型, 正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
     */
    public void onGroupMsg(String userId, String groupId, String content, String subType){
        String response = null;
        try{
            String[] params = content.split("\\s+");
            Instruction ins = Instruction.valueOf(params[0].toUpperCase());
            switch (ins){
                case INFO:
                    response = getUserInfo(params[1]).toString();
                    break;
                case MAP:
                    response = getMapInfo(params[1]).toString();
                    break;
                case SCORE:
                    response = getUserMapScore(params[1], params[2]).toString();
                    break;
                default:
                    logger.debug("不认识的指令");
            }
            cqService.sendPrivateMsg(userId, response.substring(0,500));
        }
        catch (Exception e){
            // if(e.getCause() instanceof ConnectException)
            //     response = "ppy服务器又连不上了";
            logger.debug(e.getLocalizedMessage());
        }
        cqService.sendGroupMsg(groupId, response);
    }

    public Object getUserInfo(String osuId){
        JSONObject user = osuService.getUser(osuId);
        return user;
    }

    public Object getMapInfo(String mapId){
        JSONObject map = osuService.getBeatMap(mapId);
        return map;
    }

    public Object getUserMapScore(String mapId, String osuId){
        JSONObject score = osuService.getUserBeatmapScore(mapId, osuId);
        return score;
    }
}
