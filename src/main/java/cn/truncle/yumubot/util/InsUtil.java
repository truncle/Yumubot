package cn.truncle.yumubot.util;

import cn.truncle.yumubot.controller.EventController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class InsUtil {

    private static final Logger logger = LoggerFactory.getLogger(InsUtil.class);

    public static String getIns(String rawMsg){
        String Ins = "";
        try {
            //TODO 解析消息获得指令
            Ins = "INFO";
        }
        catch (Exception e){
            logger.info("Invalid instruction");
        }
        return Ins;
    }

    public static String[] getParams(String rowMsg){
            List<String> params = new ArrayList<>();
            try{
                //TODO 解析消息获得参数
                params.add("myName");
            }catch (Exception e){
                logger.info(("Invalid instruction"));
            }
            return params.toArray(new String[0]);
    }
}
