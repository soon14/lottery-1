/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Administrator
 */
public class ImageUtils {
    /**  
    * 给图片添加水印  
    * @param iconPath 水印图片路径  
    * @param srcImgPath 源图片路径  
    * @param targerPath 目标图片路径  
    */  
   public static void markImageByIcon(String iconPath, String srcImgPath,   
           String targerPath) {   
       markImageByIcon(iconPath, srcImgPath, targerPath, null);   
   }   
 
   /**  
    * 给图片添加水印、可设置水印图片旋转角度  
    * @param iconPath 水印图片路径  
    * @param srcImgPath 源图片路径  
    * @param targerPath 目标图片路径  
    * @param degree 水印图片旋转角度  
    */  
   public static void markImageByIcon(String iconPath, String srcImgPath,   
           String targerPath, Integer degree) {   
       OutputStream os = null;   
       try {   
           Image srcImg = ImageIO.read(new File(srcImgPath));   
 
           BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),   
                   srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);   
 
           // 得到画笔对象   
           // Graphics g= buffImg.getGraphics();   
           Graphics2D g = buffImg.createGraphics();   
 
           // 设置对线段的锯齿状边缘处理   
           g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                   RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
 
           g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg   
                   .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);   
 
           if (null != degree) {   
               // 设置水印旋转   
               g.rotate(Math.toRadians(degree),   
                       (double) buffImg.getWidth() / 2, (double) buffImg   
                               .getHeight() / 2);   
           }   
 
           // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度   
           ImageIcon imgIcon = new ImageIcon(iconPath);   
 
           // 得到Image对象。   
           Image img = imgIcon.getImage();   
 
           float alpha = 1f; // 透明度   
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,   
                   alpha));   
 
           // 表示水印图片的位置   
            g.drawImage(img, 145, 145, null);   
  
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));   
  
            g.dispose();   
  
            os = new FileOutputStream(targerPath);   
  
            // 生成图片   
            ImageIO.write(buffImg, "JPG", os);   
  
            //System.out.println("图片完成添加Icon印章。。。。。。");   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (null != os)   
                    os.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }
    /**
     * 处理广告图片显示
     * @param content
     * @return
     */
    public static String dealImageUrl(String content) {
        if (content.contains("http://mmbiz.qpic.cn")) {
            return content.replaceAll("http://mmbiz.qpic.cn",
                    "http://read.html5.qq.com/image?src=fav&amp;imgflag=2&amp;imageUrl=http://mmbiz.qpic.cn");
        } else if (content.contains("https://mmbiz.qpic.cn")) {
            return content.replaceAll("https://mmbiz.qpic.cn",
                    "http://read.html5.qq.com/image?src=fav&amp;imgflag=2&amp;imageUrl=http://mmbiz.qpic.cn");
        } else if (content.contains("http://mmbiz.qlogo.cn")) {
            return content.replaceAll("http://mmbiz.qlogo.cn",
                    "http://read.html5.qq.com/image?src=fav&amp;imgflag=2&amp;imageUrl=http://mmbiz.qlogo.cn");
        } else if (content.contains("https://mmbiz.qlogo.cn")) {
            return content.replaceAll("https://mmbiz.qlogo.cn",
                    "http://read.html5.qq.com/image?src=fav&amp;imgflag=2&amp;imageUrl=https://mmbiz.qlogo.cn");
        } else if (content.contains("https://img.xiumi.us")) {
            return content.replaceAll("https://img.xiumi.us",
                    "http://read.html5.qq.com/image?src=fav&amp;imgflag=2&amp;imageUrl=https://img.xiumi.us");
        }else {
            return content;

        }
    }
}
