package cn.truncle.yumubot.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CQObject {
    private static final Logger logger = LoggerFactory.getLogger(CQObject.class);
    String CQ;//cqcode
    String qq;//@qq
    String id;//回复的msgId
    String text;
    String file;//文件
    String url;//链接
    /***
     * 返回字符串包含的cqcode
     * @param s raw_message字段
     * @return cqcode
     */
    public static CQObject parseCQObject(String s){
        Pattern pattern = Pattern.compile("(.*)?\\[CQ:(?<cqcode>\\w+)((,qq=)(?<qq>\\d+))?((,id=)(?<id>\\d+))?((,file=)(?<file>[^\\,]+))?(.*)?\\]");
        Matcher o = pattern.matcher(s);
        if (o.find()){
            CQObject cq = new CQObject();
            cq.setCQ(o.group("cqcode"));
            cq.setQq(o.group("qq"));
            cq.setId(o.group("id"));
            cq.setFile(o.group("file"));
            return cq;
        }else logger.error("无CQ码");
        return null;
    }

    /***
     * 创建@信息
     * @param qq 被@的qq号
     * @return
     */
    public static CQObject parseAt(String qq){
       return new CQObject().setCQ("at").setQq(qq);
    }

    /***
     * 回复消息 要放在信息开头
     * @param msageId 被回复的消息id
     * @return
     */
    public static CQObject parseReply(String msageId){
        return new CQObject().setCQ("reply").setQq(msageId);
    }

    /***
     * 发送本地图片
     * @param file 图片文件
     * @return
     */
    public static CQObject parseImg(File file){
        if(file != null && file.isFile())
            try {
                // 通过ImageReader来解码这个file并返回一个BufferedImage对象
                // 如果找不到合适的ImageReader则会返回null，我们可以认为这不是图片文件
                // 或者在解析过程中报错，也返回false
                Image image = ImageIO.read(file);
                if(image != null)
                    return new CQObject().setCQ("image").setFile("file:///"+file.getCanonicalPath()).setId("40000");
            } catch(IOException ex) {
                logger.error("图片异常");
            }
        return null;
    }

    /***
     * 发送缓存cq图片
     * @param file .image的文件完整名 or 图片url
     * @return
     */
    public static CQObject parseImg(String file){
        return new CQObject().setCQ("image").setFile(file).setId("40000");
    }

    public boolean hashAt(){
        if(CQ==null)return false;
        return CQ.equals("at");
    }
    public boolean hashReply(){
        if(CQ==null)return false;
        return CQ.equals("reply");
    }
    public boolean hashImg(){
        if(CQ==null)return false;
        return CQ.equals("image");
    }
    public String getCQ() {
        return CQ;
    }

    public CQObject setCQ(String CQ) {
        this.CQ = CQ;
        return this;
    }

    public String getQq() {
        return qq;
    }

    public CQObject setQq(String qq) {
        this.qq = qq;
        return this;
    }

    public String getId() {
        return id;
    }

    public CQObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public CQObject setText(String text) {
        this.text = text;
        return this;
    }

    public String getFile() {
        return file;
    }

    public CQObject setFile(String file) {
        this.file = file;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public CQObject setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        if(getCQ() == null) return null;
        StringBuffer str = new StringBuffer();
        str.append("[CQ:"+CQ);
        str.append(qq==null?"":",qq="+qq);
        str.append(id==null?"":",id="+id);
        str.append(text==null?"":",text="+text);
        str.append(file==null?"":",file="+file);
        str.append(url==null?"":",url="+url);
        str.append(']');
        return str.toString();
    }
}
