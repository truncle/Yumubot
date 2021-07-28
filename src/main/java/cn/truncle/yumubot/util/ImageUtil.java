package cn.truncle.yumubot.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    public void drop(){

    }
    /***
     * 创建空画布(透明
     * @param width 宽
     * @param height 高
     * @return
     */
    public static BufferedImage createNullImg(int width, int height){
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getG2d(img);
        img = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

        //释放资源
        g2d.dispose();
        return img;
    }
    /***
     * 创建指定色画布
     * @param width 宽
     * @param height 高
     * @param color 颜色
     * @return
     */
    public static BufferedImage createNullImg(int width, int height, Color color){
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getG2d(img);
        g2d.setColor(color);
        g2d.fillRect(0,0,width,height);
        g2d.dispose();
        return img;
    }
    /***
     * 得到该画布的画笔
     * @param b 画布
     * @return
     */
    public static Graphics2D getG2d(BufferedImage b){
        Graphics2D g2d = b.createGraphics();
        return g2d;
    }
    /***
     * 画布写为图片
     * @param bf 画布
     * @param path 图片路径
     * @param name 图片名称
     * @param type 类型(png/jpg)
     * @return 图片文件
     * @throws IOException
     */
    public static File createImgFile(BufferedImage bf, String path, String name, String type) throws IOException {
        //bf:图像缓存 path:文件路径 name:图片名 type:为png/jpg
        File output = new File(path+'\\'+name+'.'+type);
        ImageIO.write(bf, type, output);
        return output;
    }

    /***
     * 画布重新写入文件 重构方法
     * @param bf 画布
     * @param f 图片文件
     * @return
     * @throws IOException
     */
    public static File createImgFile(BufferedImage bf, File f) throws IOException {
        String fileName = f.getName();
        ImageIO.write(bf , fileName.substring(fileName.lastIndexOf(".") + 1), f);
        return f;
    }

    /***
     * 绘制文字
     * @param img 画布
     * @param str 文字
     * @param X 文字左上X坐标
     * @param Y 文字左上Y坐标
     * @param color 颜色
     * @param font 字体
     * @return
     */
    public static BufferedImage drowString(BufferedImage img, String str, int X, int Y ,Color color, Font font){
        // 字体右上角定位 x: x y: y+fongSize
        Graphics2D g2d = getG2d(img);
        //开启抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //设置字体
        g2d.setFont(font);
        //设置颜色
        g2d.setColor(color);
        //绘制
        g2d.drawString(str, X, Y+font.getSize());
        //结束
        g2d.dispose();
        return img;
    }

    /***
     * 绘制文字 右对齐
     * @param img 画布
     * @param str 文字
     * @param X 文字右上X坐标
     * @param Y 文字右上Y坐标
     * @param color 颜色
     * @param font 字体
     * @return
     */
    public static BufferedImage drowStringR(BufferedImage img, String str, int X, int Y ,Color color, Font font){
        // 字体右上角定位 x: x-width y: y+fongSize
        return drowString(img, str, X-textWidth(font,str), Y, color, font);
    }

    /***
     * 绘制文字 垂直向下排列
     * @param img 画布
     * @param str
     * @param X 文字左上X坐标
     * @param Y 文字左上Y坐标
     * @param color
     * @param font
     * @return
     */
    public static BufferedImage drowStringD(BufferedImage img, String str, int X, int Y ,Color color, Font font){
        char[] strs = str.toCharArray();
        for (int i = 0; i < strs.length; i++) {
            drowString(img, String.valueOf(strs[i]), X, Y+(i*font.getSize()), color, font);
        }
        return img;
    }

    /***
     * 加载字体文件
     * @param file 字体文件
     * @return
     * @throws IOException
     * @throws FontFormatException
     */
    public static Font getFileFont(File file) throws IOException, FontFormatException {
        Font font = null;
        if(file != null && file.isFile()){
            font = Font.createFont(Font.TRUETYPE_FONT, file);
        }
        return font;
    }

    /***
     * 加载字体文件
     * @param path 字体文件路径
     * @return
     * @throws IOException
     * @throws FontFormatException
     */
    public static Font getFileFont(String path) throws IOException, FontFormatException {
        File file = new File(path);
        Font font = null;
        if(file != null && file.isFile()){
            font = Font.createFont(Font.TRUETYPE_FONT, file);
        }
        return font;
    }

    /***
     * 渐变色组
     * @param a 初始颜色
     * @param b 结束颜色
     * @param size 颜色密度
     * @return
     */
    public static Color[] getColor(Color a, Color b,int size){
        if (size<0) new Exception("size > 0");
        Color[] colors = new Color[size];
        int[] st = new int[4];
        int[] de = new int[4];
        st[0] = a.getRed();
        st[1] = a.getGreen();
        st[2] = a.getBlue();
        st[3] = a.getAlpha();
        de[0] = (b.getRed()-st[0])/size;
        de[1] = (b.getGreen()-st[1])/size;
        de[2] = (b.getBlue()-st[2])/size;
        de[3] = (b.getAlpha()-st[3])/size;
        for(int i=0;i<size;i++) colors[i] = new Color(st[0]+i*de[0],st[1]+i*de[1],st[2]+i*de[2],st[3]+i*de[3]);
        return colors;
    }

    /***
     * 创建六边形
     * @param X 中心X坐标
     * @param Y 中心Y坐标
     * @param size 最大大小(满状态
     * @param a A属性 左上点 默认0-100
     * @param b 右上点
     * @param c 最右点
     * @param d 右下点
     * @param e 左下点
     * @param f 最左点
     * @return
     */
    public static GeneralPath creat6(int X, int Y, int size, int a, int b, int c, int d, int e, int f) {
//        使用
//        GeneralPath lbx = creat6(650, 300,100,80,85,100,85,80,80);
//        g2d.setColor(new Color(0,50,205,100));
//        设置抗锯齿
//        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//        g2d.fill(lbx);
        double[] potX = new double[6];
        double[] potY = new double[6];
        //计算数据位置
        potX[0] = size * (-0.5 * a * 0.01) + X;
        potX[1] = size * (0.5 * b * 0.01) + X;
        potX[2] = size * (c * 0.01) + X;
        potX[3] = size * (0.5 * d * 0.01) + X;
        potX[4] = size * (-0.5 * e * 0.01) + X;
        potX[5] = size * (-1 * f * 0.01) + X;

        potY[0] = size * (0.866 * a * 0.01) + Y;
        potY[1] = size * (0.866 * b * 0.01) + Y;
        potY[2] = 0 + Y;
        potY[3] = size * (-0.866 * d * 0.01) + Y;
        potY[4] = size * (-0.866 * e * 0.01) + Y;
        potY[5] = 0 + Y;

        GeneralPath lbx = new GeneralPath();
        lbx.append(new Line2D.Double(potX[0], potY[0], potX[1], potY[1]), true);
        lbx.append(new Line2D.Double(potX[1], potY[1], potX[2], potY[2]), true);
        lbx.append(new Line2D.Double(potX[2], potY[2], potX[3], potY[3]), true);
        lbx.append(new Line2D.Double(potX[3], potY[3], potX[4], potY[4]), true);
        lbx.append(new Line2D.Double(potX[4], potY[4], potX[5], potY[5]), true);
        lbx.append(new Line2D.Double(potX[5], potY[5], potX[0], potY[0]), true);
        lbx.closePath();
        return lbx;
    }

    /***
     * 读取文件为画布
     * @param path 文件路径
     * @return
     */
    public static BufferedImage openFile(String path){
        File file = new File(path);
        BufferedImage bmg = null;
        if(file!=null && file.isFile()){
            try {
                bmg = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bmg;
    }

    /***
     * 画布叠加
     * @param to 底层
     * @param from 上层
     * @param x 上层(0,0)位于底层相对X坐标
     * @param y 上层(0,0)位于底层相对Y坐标
     * @return
     */
    public static BufferedImage drowImg(BufferedImage to, BufferedImage from, int x, int y){
        Graphics2D g2d = to.createGraphics();
        g2d.drawImage(from, null, x, y);
        g2d.dispose();
        return to;
    }

    /***
     * 画布叠加 上层增加透明度
     * @param to 底层
     * @param from 上层
     * @param x 上层(0,0)位于底层相对X坐标
     * @param y 上层(0,0)位于底层相对Y坐标
     * @param alpha 透明度(0f-1f)
     * @return
     */
    public static BufferedImage drowImg(BufferedImage to, BufferedImage from, int x, int y, float alpha){
        Graphics2D g2d = to.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,  alpha));
        g2d.drawImage(from, null, x, y);
        g2d.dispose();
        return to;
    }

    /***
     * 画布重缩放
     * @param from 原画布
     * @param width 新画布大小
     * @param height 新画布大小
     * @return
     */
    public static BufferedImage scaleImg(BufferedImage from, int width, int height){
        // *     SCALE_AREA_AVERAGING: 使用 Area Averaging 图像缩放算法;
        // *     SCALE_DEFAULT: 使用默认的图像缩放算法;
        // *     SCALE_SMOOTH: 选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
        if(from == null) return null;
        Image to = from.getScaledInstance(width, height, BufferedImage.SCALE_FAST);
        BufferedImage next = createNullImg(width, height);
        Graphics2D g2d = getG2d(next);
        g2d.drawImage(to,0,0, null);
        g2d.dispose();
        return next;
    }

    /***
     * 获得绘制文字宽度
     * @param font
     * @param str
     * @return
     */
    public static int textWidth(Font font, String str){
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
        int width = SwingUtilities.computeStringWidth(fm, str);
        return width;
    }

}
