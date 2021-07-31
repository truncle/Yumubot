package cn.truncle.yumubot.util;

import cn.truncle.yumubot.entity.BinUser;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class BindingUtil {
    private final Logger log = LoggerFactory.getLogger(BindingUtil.class);



    @Value("${yumubot.osu-v2.binddir}")
    String dirPath;
    @Async
    public void Write(BinUser user) {
        File x = new File(dirPath + user.getQq() + ".json");
        try {
            if (x.isFile()) {
                FileWriter writer = new FileWriter(x);
                writer.write(JSONObject.toJSONString(user));
                writer.flush();
                writer.close();
            } else {
                x.createNewFile();
                FileWriter writer = new FileWriter(x);
                writer.write(JSONObject.toJSONString(user));
                writer.flush();
                writer.close();

            }
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
    }
    @Async
    public BinUser getUser(long qq){
        File x = new File(dirPath + qq + ".json");
        try {
            if (x.isFile()) {
                FileReader reader = new FileReader(x);
                BufferedReader bf=new BufferedReader(reader);
                String line,str="";
                while((line=bf.readLine())!=null){
                    str=str+line;
                }
                return JSONObject.parseObject(str, BinUser.class);
            } else {
                throw new IOException("user not find");
            }
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
        return null;
    }

}
