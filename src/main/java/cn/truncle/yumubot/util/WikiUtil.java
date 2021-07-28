package cn.truncle.yumubot.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;

import java.io.IOException;

public class WikiUtil {
    private JSONObject data;
    public WikiUtil(){
        ClassPathResource classPathResource = new ClassPathResource("wiki.json");
        try {
            byte[] databyte = classPathResource.getInputStream().readAllBytes();
        data = (JSONObject) JSONObject.parse(databyte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getWiki(@Nullable String key){
        key = key.trim().toUpperCase();
        if (key == null || key.equals("") || key.equals("INDEX")){
            StringBuffer sb = new StringBuffer();
            for(Object data1 : data.getJSONArray("INDEX")){
                JSONObject c = (JSONObject) data1;
                sb.append(c.keySet().iterator().next());
                c.getJSONArray(c.keySet().iterator().next()).forEach(s -> sb.append("\n-"+s.toString()));
                sb.append('\n');
            }
            return sb.toString();
        }
        String s = data.getString(key);
        if(s != null) return s;
        else return "暂时找不到该词条";
    }
}
