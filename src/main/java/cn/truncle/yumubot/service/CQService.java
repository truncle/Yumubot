package cn.truncle.yumubot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class CQService {
    private static final Logger logger = LoggerFactory.getLogger(CQService.class);

    //从配置文件读取服务器地址
    @Value("${yumubot.go-cqhttp.address}")
    private String address;
    @Value("${yumubot.go-cqhttp.filePath}")
    private String filePath;
    @Autowired
    RestTemplate restTemplate;

    //TODO sendMessage改成建造者模式,只留一个方法

    /**
     * 发送私聊消息
     * @param userId QQ号
     * @param message 要发送的消息
     * @return messageId 消息id
     */
    public int sendPrivateMsg(String userId, String message){
        if(message == null || userId == null) {
            logger.error("?");
            return 0;
        }
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("send_private_msg")
                .queryParam("user_id", userId)
                .queryParam("message", message)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")) {
            return rest.getJSONObject("data").getIntValue("message_id");
        }else {
            logger.error(rest.toJSONString());
            return 0;
        }
    }

    /***
     * 通过群聊发送消息 针对于未加好友无法发送消息
     * @param userId QQ号
     * @param groupId 群号
     * @param message 消息
     * @return messageId 消息id
     */
    public int sendPrivateMsg(String userId, String groupId, String message){
        if(message == null || userId == null || groupId == null) {
            logger.error("?");
            return 0;
        }
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("send_private_msg")
                .queryParam("user_id", userId)
                .queryParam("group_id", groupId)
                .queryParam("message", message)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")) {
            return rest.getJSONObject("data").getIntValue("message_id");
        }else {
            logger.error(rest.toJSONString());
            return 0;
        }
    }
    /**
     * 发送群聊消息
     * @param groupId 群号
     * @param message 要发送的消息
     * @return messageId 消息id
     */
    public int sendGroupMsg(String groupId, String message){
        if(message==null || groupId == null) {
            logger.error("?");
            return 0;
        }
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("send_group_msg")
                .queryParam("group_id", groupId)
                .queryParam("message", message)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")) {
            return rest.getJSONObject("data").getIntValue("message_id");
        }else {
            logger.error(rest.toJSONString());
            return 0;
        }
    }

    /***
     * 撤回消息
     * @param messageId 消息id
     * @return 是否成功
     */
    public boolean deleteMsg(String messageId){
        if(messageId==null ) {
            logger.error("?");
            return false;
        }
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("delete_msg")
                .queryParam("message_id", messageId)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")){
            return true;
        }else {
            logger.error(rest.toJSONString());
            return false;
        }
    }

    /***
     * 查询消息详情
     * @param messageId 消息id
     * @return json{
     * message_id	int32	消息id
     * real_id	    int32	消息真实id
     * sender	    object	发送者
     * time	        int32	发送时间
     * message	    message	消息内容
     * raw_message	message	原始消息内容
     * }
     */
    public JSONObject getMsg(String messageId){
        if(messageId==null ) {
            logger.error("?");
            return null;
        }
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("delete_msg")
                .queryParam("message_id", messageId)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")) {
            return rest.getJSONObject("data");
        }else {
            logger.error(rest.toJSONString());
            return null;
        }
    }

    /**
     * 获得图片消息的详细信息
     * @param fileName 完整文件名(***.image)
     * @return 图片文件
     */
    public File getImage(String fileName){
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("get_image")
                .queryParam("file", fileName)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        System.out.println(rest);
        if(rest.getJSONObject("data") != null){
            byte[] imageByte = restTemplate.getForObject(rest.getJSONObject("data").getString("url"), byte[].class);
            File file = new File(filePath+rest.getJSONObject("data").getString("filename"));
            FileOutputStream out = null;
            try {
                file.createNewFile();
                out = new FileOutputStream(file);
                out.write(imageByte);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }else logger.error(rest.toJSONString());
        return null;
    }

    /***
     * 踢人!
     * @param groupId 群号
     * @param userId qq号
     * @return
     */
    public boolean setGroupKick(String groupId, String userId){
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("set_group_kick")
                .queryParam("group_id", groupId)
                .queryParam("user_id", userId)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")){
            return true;
        }else {
            logger.error(rest.toJSONString());
            return false;
        }
    }

    /***
     * 设置群名片
     * @param groupId 群号
     * @param userId   qq号
     * @param card  群名片
     * @return
     */
    public boolean setGroupCard(String groupId, String userId, String card){
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("set_group_card")
                .queryParam("group_id", groupId)
                .queryParam("user_id", userId)
                .queryParam("card", card)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")){
            return true;
        }else {
            logger.error(rest.toJSONString());
            return false;
        }
    }

    /***
     * 设置禁言
     * @param groupId 群号
     * @param userId    qq号
     * @param duration 时间 秒数,0为解除禁言
     * @return
     */
    public boolean setGroupBan(String groupId, String userId,String duration){
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("set_group_ban")
                .queryParam("group_id", groupId)
                .queryParam("user_id", userId)
                .queryParam("duration", duration)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")){
            return true;
        }else {
            logger.error(rest.toJSONString());
            return false;
        }
    }

    /***
     * 设置全体禁言
     * @param groupId 群号
     * @param enable 是否全体禁言
     * @return
     */
    public boolean setGroupWholeBan(String groupId, boolean enable){
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("set_group_whole_ban")
                .queryParam("group_id", groupId)
                .queryParam("enable", enable)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")){
            return true;
        }else {
            logger.error(rest.toJSONString());
            return false;
        }
    }

    /***
     * 获得好友列表
     * @return [{
     *  user_id	    int64	QQ 号
     *  nickname	string	QQ 昵称
     * },...]
     */
    public JSONArray getFriendList(){
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("get_friend_list")
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")) {
            return rest.getJSONArray("data");
        }else {
            logger.error(rest.toJSONString());
            return null;
        }
    }

    /***
     * 获得群列表
     * @return [{
     * group_id	            int64	群号
     * group_name	        string	群名称
     * group_memo	        string	群备注
     * group_create_time	uint32	群创建时间
     * group_level      	uint32	群等级
     * member_count     	int32	成员数
     * max_member_count 	int32	最大成员数（群容量）
     * },...]
     */
    public JSONArray getGroupList(){
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("get_group_list")
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")) {
            return rest.getJSONArray("data");
        }else {
            logger.error(rest.toJSONString());
            return null;
        }
    }

    /***
     * 获得群成员信息
     * @param groupId 群号
     * @return [{
     * group_id	        int64	群号
     * user_id	        int64	QQ 号
     * nickname	        string	昵称
     * card	            string	群名片／备注
     * sex	            string	性别, male 或 female 或 unknown
     * age	            int32	年龄
     * area	            string	地区
     * join_time	    int32	加群时间戳
     * last_sent_time	int32	最后发言时间戳
     * level	        string	成员等级
     * role	            string	角色, owner 或 admin 或 member
     * unfriendly	    boolean	是否不良记录成员
     * title	        string	专属头衔
     * title_expire_time	int64	专属头衔过期时间戳
     * card_changeable	boolean	是否允许修改群名片
     * },...]
     */
    public JSONArray getGroupMemberList(String groupId){
        UriComponents uri = UriComponentsBuilder
                .newInstance()
                .scheme("Http")
                .host(address)
                .path("get_group_member_list")
                .queryParam("group_id", groupId)
                .build();
        JSONObject rest = restTemplate.getForObject(uri.toUriString(), JSONObject.class);
        if(rest != null && rest.getString("status").equals("ok")) {
            return rest.getJSONArray("data");
        }else {
            logger.error(rest.toJSONString());
            return null;
        }
    }

}
