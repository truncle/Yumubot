package cn.truncle.yumubot.util;

public enum Instruction {
    //help的时候把这里打印出来
    INFO("getInfo", "获取用户信息"),
    SCORE("getScore", "获取成绩"),
    MAP("getMap", "获取Map信息");

    private final String method;

    private final String desc;

    Instruction(String method, String desc){
        this.method = method;
        this.desc = desc;
    }

    public String getMethod(){
        return method;
    }

    public String getDesc(){
        return desc;
    }

}
