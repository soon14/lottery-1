package com.oruit.app.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.oruit.app.util.JsonDealUtil;
import org.apache.log4j.Logger;

public class JsonResponseHandler {

    private static final Logger log = Logger.getLogger(JsonResponseHandler.class);
    private static final Map<String, ResponseHandler<?>> map = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> ResponseHandler<T> createResponseHandler(final Class<T> clazz) {

        if (map.containsKey(clazz.getName())) {
            return (ResponseHandler<T>) map.get(clazz.getName());
        } else {
            ResponseHandler<T> responseHandler = new ResponseHandler<T>() {
                @Override
                public T handleResponse(HttpResponse response)
                        throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        String str = EntityUtils.toString(entity,"utf-8");
                        log.debug("-----------微信返回参数---------------"+str);
                        return JsonDealUtil.parseObject(str, clazz);
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            map.put(clazz.getName(), responseHandler);
            return responseHandler;
        }
    }

}
