/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.util.Random;

/**
 * 随机生成动态验证码
 *
 * @author Liuk
 */
public class VCodeUtil {

    public static String getVCode() {
        Random random = new Random();
        String vCode = "";
        for (int i = 0; i < 4; i++) {
            vCode += random.nextInt(10);
        }
        return vCode;
    }
    
     public static String getVCode(int j) {
        Random random = new Random();
        String vCode = "";
        for (int i = 0; i < j; i++) {
            vCode += random.nextInt(10);
        }
        return vCode;
    }
}
