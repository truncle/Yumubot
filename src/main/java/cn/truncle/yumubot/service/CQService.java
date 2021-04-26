package cn.truncle.yumubot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CQService {
    private static final Logger logger = LoggerFactory.getLogger(CQService.class);

    //从配置文件读取服务器地址
    @Value("${yumubot.go-cqhttp.address}")
    private String address;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 发送私聊消息
     * @param userId QQ号
     * @param message 要发送的消息
     */
    public void sendPrivateMsg(Integer userId, String message){
        if(message==null)
            return;
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("send_private_msg")
                .queryParam("user_id", userId)
                .queryParam("message", message)
                .build();
        restTemplate.getForObject(uri.toUriString(), Object.class);
    }

    /**
     * 发送群聊消息
     * @param groupId 群号
     * @param message 要发送的消息
     */
    public void sendGroupMsg(Integer groupId, String message){
        if(message==null)
            return;
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("send_group_msg")
                .queryParam("group_id", groupId)
                .queryParam("message", message)
                .build();
        restTemplate.getForObject(uri.toUriString(), Object.class);
    }
}
