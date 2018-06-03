package com.xuchen.util;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

public class CutImgUtil {

    /**
     * @param bufferedImage 原图片流
     * @param imgType  图片后缀
     * @param resultFile  图片存储文件
     * @param width 缩放后的宽
     * @param height 缩放后的高
     * @return
     */
    public static File coverImg(BufferedImage bufferedImage, String imgType, int width, int height, File resultFile) throws Exception {
        if(width<=0||height<=0){
            throw new Exception("宽高必须都大于0");
        }
        bufferedImage.releaseWritableTile(width,height);
        BufferedImage newBufferedImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newBufferedImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.drawImage(bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        try {
            ImageIO.write(newBufferedImage, imgType, resultFile);
        } catch (IOException e) {
            throw e;
        }
        return resultFile;
    }

    /**
     *
     * @param bufferedImage 原图片流
     * @param imgType  图片后缀
     * @param resultFile  图片存储文件
     * @param percent 缩放比例
     * @return
     */
    public static File zipImg(BufferedImage bufferedImage, String imgType,  double percent,File resultFile) throws Exception {
        int width= (int) (bufferedImage.getWidth()*percent);
        int height= (int) (bufferedImage.getHeight()*percent);
        if (percent<=0){
            throw new Exception("缩放比例不能小于等于0");
        }
        if (width==0||height==0){
            throw new Exception("缩放的比例过小");
        }
        return coverImg(bufferedImage,imgType,width,height,resultFile);
    }

    /**
     *  切割图片
     * @param bufferedImage 原图片流
     * @param imgType  图片后缀
     * @param resultFile  图片存储位置(含名称后缀)
     * @return
     */
    public static File cutImg(BufferedImage bufferedImage, String imgType, int x, int y, int width, int height,File resultFile) throws Exception {
        int srcWidth = bufferedImage.getWidth(); // 源图宽度
        int srcHeight = bufferedImage.getHeight(); // 源图高度
        if (srcHeight<(x+width)||srcHeight<(y+height)){
            throw new Exception("切割后的图片超出原图尺寸");
        }
        Image image = bufferedImage.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
        // 四个参数分别为图像起点坐标和宽高
        ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
        Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 绘制切割后的图
        Graphics g = tag.getGraphics();
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();
        // 输出为文件
        ImageIO.write(tag, imgType, resultFile);
        return resultFile;
    }
}
