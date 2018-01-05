package com.oruit.app.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by wyt on 2017-11-14.
 */
public class IOJSONObject {
    /**
     * 读接口请求流 ， 并转换成JSONObject
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static  String getRequestParams(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
        StringBuilder builder;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"))) {
            builder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            System.out.println("---------------------------"+builder.toString());
        }
        return builder.toString();
    }
}
