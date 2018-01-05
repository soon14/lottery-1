package com.oruit.app.http.test;

import com.oruit.app.oruitkey.OruitBase64;
import com.oruit.app.oruitkey.OruitKey;
import java.util.Random;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * 编码工具类
 * 1.将byte[]转为各种进制的字符串
 * 2.base 64 encode
 * 3.base 64 decode
 * 4.获取byte[]的md5值
 * 5.获取字符串md5值
 * 6.结合base64实现md5加密
 * 7.AES加密
 * 8.AES加密为base 64 code
 * 9.AES解密
 * 10.将base 64 code AES解密
 * @author uikoo9
 * @version 0.0.7.20140601
 */
public class HongBaoTest {
	
	public static void main(String[] args) throws Exception {
//            String result = OruitKey.encryptForShare("ruitonnasdaq", "userid=150483&pf=llzhb");
//            System.out.print(result);
              BASE64Encoder baseEncoder = new BASE64Encoder();
              BASE64Decoder baseDecoder = new BASE64Decoder();
              System.out.print(baseEncoder.encode("========".getBytes()));
              System.out.print( new String(baseDecoder.decodeBuffer(baseEncoder.encode("========".getBytes())), "utf-8"));
//org.apache.commons.codec.binary.Base64.isBase64(String base64); 
	}
	
}
