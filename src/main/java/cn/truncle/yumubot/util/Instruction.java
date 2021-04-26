package cn.truncle.yumubot.util;

public enum Instruction {
    INFO("info"),
    SCORE("score"),
    MAP("map");

    private String value;

    Instruction(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
