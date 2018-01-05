/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.image.util;



import com.oruit.app.oss.OOSManager;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * @Description:图片处理工具
 * @author:liuyc
 * @time:2016年5月27日 上午10:18:00
 */
public class ImageHandleHelper {

    /**
     * @param srcFile 源图片
     * @param targetFile 截好后图片全名
     * @param startAcross 开始截取位置横坐标
     * @param StartEndlong 开始截图位置纵坐标
     * @param width 截取的长
     * @param hight 截取的高
     * @throws java.lang.Exception
     * @Description:截图
     * @time:2016年5月27日 上午10:18:23
     */
    public static void cutImage(String srcFile, String targetFile, int startAcross, int StartEndlong, int width,
                                int hight) throws Exception {
        // 取得图片读入器
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = readers.next();
        // 取得图片读入流
        InputStream source = new FileInputStream(srcFile);
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        // 图片参数对象
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(startAcross, StartEndlong, width, hight);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, targetFile.split("\\.")[1], new File(targetFile));
    }

    /**
     * @Description:图片拼接 （注意：必须两张图片长宽一致哦）
     * @author:liuyc
     * @time:2016年5月27日 下午5:52:24
     * @param files 要拼接的文件列表
     * @param type 横向拼接， 2 纵向拼接
     * @param targetFile
     */
    public static void mergeImage(String[] files, int type, String targetFile) {
        int len = files.length;
        if (len < 1) {
            throw new RuntimeException("图片数量小于1");
        }
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            try {
                src[i] = new File(files[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }
        int newHeight = 0;
        int newWidth = 0;
        for (int i = 0; i < images.length; i++) {
            // 横向
            if (type == 1) {
                newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                newWidth += images[i].getWidth();
            } else if (type == 2) {// 纵向
                newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
                newHeight += images[i].getHeight();
            }
        }
        if (type == 1 && newWidth < 1) {
            return;
        }
        if (type == 2 && newHeight < 1) {
            return;
        }

        // 生成新图片
        try {
            BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                if (type == 1) {
                    ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
                            images[i].getWidth());
                    width_i += images[i].getWidth();
                } else if (type == 2) {
                    ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
                    height_i += images[i].getHeight();
                }
            }
            //输出想要的图片
            ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:小图片贴到大图片形成一张图(合成)
     * @param bigPath 大图
     * @param smallPath 小图
     * @param outFile 输入文章
     * @author:liuyc
     * @time:2016年5月27日 下午5:51:20
     */
    public static final String overlapImage(String oospath,String bigPath, String smallPath, String outFile) {
        String uploadFile = "";
        try {

            BufferedImage big = ImageIO.read(new URL(bigPath));
            BufferedImage small = ImageIO.read(new URL(smallPath));
            Graphics2D g = big.createGraphics();
            int x = (big.getWidth() - small.getWidth()) / 2;
            int y = (int)((big.getHeight() - small.getHeight()) / 2.65);
            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
            g.dispose();
            File file = new File(outFile);
            ImageIO.write(big, outFile.split("\\.")[1],file );

            try {
                File files = new File(String.valueOf(file));
                uploadFile = OOSManager.uploadFile(files , oospath);
                System.err.println("uploadFile:"+uploadFile);
                file.delete();
                return uploadFile;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return uploadFile;
    }

    public static void main(String[] args) throws Exception
    {
        //overlapImage("C:\\Users\\hanfeng\\Desktop\\3.png","C:\\Users\\hanfeng\\Desktop\\2.png","C:\\1\\5.jpg");
    }
}
