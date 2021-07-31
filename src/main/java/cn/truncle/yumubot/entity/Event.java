package cn.truncle.yumubot.entity;

public class Event {
    public enum PostType{
        MESSAGE("message"),
        NOTICE("notice");
        private final String value;
        PostType(String value) {
            this.value = value;
        }
    }
    public enum MessageType{
        GROUP("group"),
        PRIVATE("private");
        private final String value;
        MessageType(String value){
            this.value = value;
        }
    }
    public enum SubType{
        FRIEND("friend"),
        GROUP("group"),
        GROUP_SELF("group_self"),
        NORMAL("normal"),
        ANONYMOUS("anonymous"),
        NOTICE("notice"),
        OTHER("other");
        private final String value;
        SubType(String value){
            this.value = value;
        }
    }


    /*** 属性依次为:
     * 事件发生的时间戳
     * 收到事件的机器人 QQ 号
     * 上报类型
     * 消息类型
     * 消息子类型 pritive: 如果是好友则是 friend, 如果是群临时会话则是 group, 如果是在群中自身发送则是 group_self |group:正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
     */
    String time;
    String selfId;
    PostType postType;
    MessageType messageType;
    SubType subType;
    /***
     * 消息 ID
     * 发送者 QQ 号
     * 群号
     * 临时会话来源
     */
    String message_id;
    String user_id;
    String group_id;
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
    int temp_source;

    /***
     * 匿名信息, 如果不是匿名消息则为 null
     * 消息内容
     * 原始消息内容
     */
    String anonymous;
    String message;
    String raw_message;

    /***
     *
     */
}

