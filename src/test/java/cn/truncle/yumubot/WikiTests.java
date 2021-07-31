package cn.truncle.yumubot;

import cn.truncle.yumubot.service.CQService;
import cn.truncle.yumubot.util.ImageUtil;
import cn.truncle.yumubot.util.WikiUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class WikiTests {
    private static final Logger logger = LoggerFactory.getLogger(YumubotApplicationTests.class);

    @Autowired
    WikiUtil wikiUtil;
    @Test
    void contextLoads() {
//        logger.info(wikiUtil.getWiki(""));
//        logger.info(wikiUtil.getWiki("ACC"));

        String c = "  ！ Ym  sss ";
        Matcher matcher = Pattern.compile("[!！]\\s?[yY][mM]\\s*(?<a>\\w+)\\s*(?<b>.*)?").matcher(c);
        if(matcher.find()){
            logger.info(c);
            logger.info('|'+matcher.group("a")+'|');
            logger.info(String.valueOf(matcher.group("b").equals("")));
        }
    }
    @Autowired
    CQService cqService;
//    void gogo(){
//        String[] words = cqService.getWordSlices("1.单双：低密度用固定的手指（俗称主指）点击，遇到连打时，引入另一根手指，从主指开始交替点击。优点：主指按下的节奏稳定，用脑程度低，成绩准确率高。缺点：主指压力容易过大。（也可以利用换主指打低密度段的方法来解决）\n" +
//                "2.强双：不论何时，均用两根手指交替点击。优点：双指压力均匀，耐久高。缺点：需要投入大量时间练习，用脑程度高，练习不够容易脑梗卡住从而断连。\n" +
//                "3.屌双：位于强双和单双之间的一种手法，低密度时交替点击，遇到连打时，从主指开始交替点击。优点：综合利用了强双单双的优势且可以遇到特殊段落快速切换手法。缺点：用脑程度较高，也容易卡指。4.混双：位于强双和单双之间，低密度时交替点击，连打时起指无规律。混双的两个按键数会偏差很多。优缺点与屌双近似。\n" +
//                "5.Azuki：低密度时用移动工具点击，遇到连打时用键盘交替点击。优点：用脑程度较低，且不会出现双刀的连打移动问题。缺点：负责移动的手压力会较大，特别是打跳图的时候。\n" +
//                "6.双刀：用移动工具和键盘上的单个键交替点击。同样可以归类到单强混屌内。优点：擅长于左右开弓的玩家会很快上手。缺点：打串时，很容易移偏从而断连。\n" +
//                "7.触屏：使用触摸屏点击。优点：跳图难度降三星。缺点：连打难度升三星。而且没有 PP。\n" +
//                "其他少数人的点击移动方式不加赘述。".replaceAll("\n","").replaceAll(" ",""));
//        TreeMap<String, Integer> x = new TreeMap<>();
//        for (String word : words) {
//            if (x.get(word) == null) x.put(word,1);
//            else x.put(word,x.get(word)+1);
//        }
//
//        List<Map.Entry<String, Integer>> list = new ArrayList<>(x.entrySet());
//        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
//            //升序排序
//            public int compare(Map.Entry<String, Integer> o1,
//                               Map.Entry<String, Integer> o2) {
//                return o1.getValue().compareTo(o2.getValue());
//            }
//        });
//        for(Map.Entry<String,Integer> mapping:list){
//            System.out.println(mapping.getKey()+":"+mapping.getValue());
//        }
//    }
    @Test
    void c(){
        File c = null;
        try {
            c = ImageUtil.createImgFile(ImageUtil.createNullImg(1300,600), System.getProperty("user.dir"), "test1","png");
        } catch (Exception e){
            e.printStackTrace();
        }
        BufferedImage b = ImageUtil.createNullImg(1300,600);
        Graphics2D g2d = ImageUtil.getG2d(b);
        g2d.setColor(new Color(255,255,255));
        g2d.fillRect(0,0,650,600);
        g2d.setColor(new Color(0, 74, 25, 185));
        g2d.fillRect(650,0,650,600);


        Font font = null;
        try {
            font = ImageUtil.getFileFont(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\font\\al.ttf"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.PLAIN,50);
        String str = "■test:这是一段测试文字 123 -!@#$% (\\/)\0|";
        Color co = new Color(0,0,0,60);
        b = ImageUtil.drowString(b,str,150,150, co, font);
        b = ImageUtil.drowStringD(b,"垂直测试",0,0,co,font);
//        b = drowStr(b,"渐",150,200,new Color(0,155,155),font);
//        b = drowStr(b,"变",200,200,new Color(40,115,155),font);
//        b = drowStr(b,"色",250,200,new Color(80,75,155),font);
//        b = drowStr(b,"测",300,200,new Color(120,35,155),font);
//        b = drowStr(b,"试",350,200,new Color(120,35,195),font);

//        BufferedImage sy = ImageUtil.openFile("A:/test.png");
//        sy = ImageUtil.scaleImg(sy, 100, 100);
//        b = ImageUtil.drowImg(b, sy, 60, 180, 1f);
        g2d.dispose();
        try {
            ImageUtil.createImgFile(b,c);
            System.out.println(c.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
