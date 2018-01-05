package com.oruit.app.image.util;

import java.io.*;
import java.util.Date;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.*;

/**
 * 图片压缩处理
 *
 * @author 崔素强
 */
public class ImgCompress {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
    }

//    /**
//     * 构造函数
//     */
//    public ImgCompress(String fileName) throws IOException {
//        File file = new File(fileName);// 读入文件
//        img = ImageIO.read(file);      // 构造Image对象
//        width = img.getWidth(null);    // 得到源图宽
//        height = img.getHeight(null);  // 得到源图长
//    }
//
//    /**
//     * 按照宽度还是高度进行压缩
//     *
//     * @param w int 最大宽度
//     * @param h int 最大高度
//     */
//    public static void resizeFix(int w, int h) throws IOException {
//        if (width / height > w / h) {
//            resizeByWidth(w);
//        } else {
//            resizeByHeight(h);
//        }
//    }
//
//    /**
//     * 以宽度为基准，等比例放缩图片
//     *
//     * @param w int 新宽度
//     */
//    public static void resizeByWidth(int w) throws IOException {
//        int h = (int) (height * w / width);
//        resize(w, h);
//    }
//
//    /**
//     * 以高度为基准，等比例缩放图片
//     *
//     * @param h int 新高度
//     */
//    public static void resizeByHeight(int h) throws IOException {
//        int w = (int) (width * h / height);
//        resize(w, h);
//    }
//
//    /**
//     * 强制压缩/放大图片到固定的大小
//     *
//     * @param w int 新宽度
//     * @param h int 新高度
//     */
//    public static void resize(int w, int h) throws IOException {
//        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
//        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
//        File destFile = new File("C:\\temp\\456.jpg");
//        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
//        // 可以正常实现bmp、png、gif转jpg
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//        encoder.encode(image); // JPEG编码
//        out.close();
//    }
//    
    public static void CompressFixedSize(int w, int h, String filepath) {
        try {
            //读取图片
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filepath));
            //字节流转图片对象
            Image bi = ImageIO.read(in);
            //构建图片流
            BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, w, h, null);
            //输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filepath));
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "jpg", out);
            in.close();
            out.close();
        } catch (IOException ex) {

        }
    }
}
