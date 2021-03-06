package cn.truncle.yumubot.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Event {
//    public enum PostType{
//        MESSAGE("message"),
//        NOTICE("notice");
//        private final String value;
//        PostType(String value) {
//            this.value = value;
//        }
//    }
//    public enum MessageType{
//        GROUP("group"),
//        PRIVATE("private");
//        private final String value;
//        MessageType(String value){
//            this.value = value;
//        }
//    }
//    public enum SubType{
//        FRIEND("friend"),
//        GROUP("group"),
//        GROUP_SELF("group_self"),
//        NORMAL("normal"),
//        ANONYMOUS("anonymous"),
//        NOTICE("notice"),
//        OTHER("other");
//        private final String value;
//        SubType(String value){
//            this.value = value;
//        }
//    }
//

    /*** 属性依次为:
     * 事件发生的时间戳
     * 收到事件的机器人 QQ 号
     * 上报类型
     * 消息类型
     * 消息子类型 pritive: 如果是好友则是 friend, 如果是群临时会话则是 group, 如果是在群中自身发送则是 group_self |group:正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
     */
    private String time;
    @JSONField(name = "self_id")
    private String selfId;
    @JSONField(name = "post_type")
    private String postType;
    @JSONField(name = "message_type")
    private String messageType;
    @JSONField(name = "sub_type")
    private String subType;
    /***
     * 消息 ID
     * 发送者 QQ 号
     * 群号
     * 临时会话来源
     */
    @JSONField(name = "message_id")
    private String messageId;
    @JSONField(name = "user_id")
    private String userId;
    @JSONField(name = "group_id")
    private String groupId;
    /***
     * temp_source:
     * 0	群聊
     * 1	QQ咨询
     * 2	查找
     * 3	QQ电影
     * 4	热聊
     * 6	验证消息
     * 7	多人聊天
     * 8	约会
     * 9	通讯录
     */
    @JSONField(name = "temp_source")
    private int tempSource;

    /***
     * 匿名信息, 如果不是匿名消息则为 null
     * 消息内容
     * 原始消息内容
     */
    private String anonymous;
    private String message;
    @JSONField(name = "raw_message")
    private String rawMessage;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getTempSource() {
        return tempSource;
    }

    public void setTempSource(int tempSource) {
        this.tempSource = tempSource;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }
}

