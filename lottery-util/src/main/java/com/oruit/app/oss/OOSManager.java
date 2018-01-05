package com.oruit.app.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.oruit.app.util.web.ConfigUtil;

/**
 * @author: wangyt
 * @version:2017年11月24日 下午1:54:35
 * com.kp.alioos.OOSManager.java
 */
public class OOSManager {

    /**
     *
     * @param file
     * @param remotePath
     * @return
     * @throws Exception
     */
    public static String uploadFile(File file, String remotePath) throws Exception {

        Properties pprVue = ConfigUtil.getPprVue("config.properties");
        String endpoint = pprVue.getProperty("EndPoint");
        String accessKeyId = pprVue.getProperty("AccessKeyId");
        String accessKeySecret = pprVue.getProperty("AccessKeySecret");
        String bucketName = pprVue.getProperty("BucketName");
        String accessUrl = "http://" + bucketName + "." + endpoint;
        InputStream fileContent = null;
        fileContent = new FileInputStream(file);
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String remoteFilePath = remotePath.substring(0, remotePath.length()).replaceAll("\\\\", "/") + "/";
        //System.err.println("remoteFilePath:" + remoteFilePath);
        //创建上传Object的Metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileContent.available());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentEncoding("utf-8");
        objectMetadata.setContentType(contentType(file.getName().substring(file.getName().lastIndexOf(".") + 1)));
        objectMetadata.setContentDisposition("inline;filename=" + file.getName());
        //上传文件
        PutObjectResult putObject = ossClient.putObject(bucketName, remoteFilePath + file.getName(), fileContent, objectMetadata);
        return accessUrl + "/" + remoteFilePath + file.getName();
    }

    public static String contentType(String FilenameExtension) {
        if (FilenameExtension.equals("BMP") || FilenameExtension.equals("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals("GIF") || FilenameExtension.equals("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equals("JPEG") || FilenameExtension.equals("jpeg") ||
                FilenameExtension.equals("JPG") || FilenameExtension.equals("jpg") ||
                FilenameExtension.equals("PNG") || FilenameExtension.equals("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals("HTML") || FilenameExtension.equals("html")) {
            return "text/html";
        }
        if (FilenameExtension.equals("TXT") || FilenameExtension.equals("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equals("VSD") || FilenameExtension.equals("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals("PPTX") || FilenameExtension.equals("pptx") ||
                FilenameExtension.equals("PPT") || FilenameExtension.equals("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals("DOCX") || FilenameExtension.equals("docx") ||
                FilenameExtension.equals("DOC") || FilenameExtension.equals("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equals("XML") || FilenameExtension.equals("xml")) {
            return "text/xml";
        }
        return "text/html";
    }


}
