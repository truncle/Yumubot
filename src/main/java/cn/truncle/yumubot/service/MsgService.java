package cn.truncle.yumubot.service;

import com.alibaba.fastjson.JSONObject;

public interface MsgService {
    void getMessage(JSONObject message, String text);
}
