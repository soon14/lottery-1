package com.oruit.app.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class LocalHttpClient {

    private static final Logger log = Logger.getLogger(LocalHttpClient.class);

    protected static HttpClient httpClient = HttpClientFactory.createHttpClient(100, 10);

    private static final Map<String, HttpClient> httpClient_mchKeyStore = new HashMap<>();

    public static void init(int maxTotal, int maxPerRoute) {
        httpClient = HttpClientFactory.createHttpClient(maxTotal, maxPerRoute);
    }

    /**
     * 初始化 MCH HttpClient KeyStore
     *
     * @param mch_id
     * @param keyStoreFilePath
     */
    public static void initMchKeyStore(String mch_id, String keyStoreFilePath) {
        FileInputStream instream = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            log.debug("----------------------pkcs-------" + keyStoreFilePath);
            instream = new FileInputStream(new File(keyStoreFilePath));
            keyStore.load(instream, mch_id.toCharArray());
            instream.close();
            HttpClient httpClient = HttpClientFactory.createKeyMaterialHttpClient(keyStore, mch_id, new String[]{"TLSv1"});
            httpClient_mchKeyStore.put(mch_id, httpClient);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            log.debug("----错误信息--", e);
        } catch (FileNotFoundException e) {
            log.debug("----错误信息--", e);
        } catch (IOException e) {
            log.debug("----错误信息--", e);
        }
    }

    public static HttpResponse execute(HttpUriRequest request) {
        try {
            return httpClient.execute(request);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String executeStr(HttpUriRequest request) {
        //  处理请求结果
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response) throws IOException {
                int status = response.getStatusLine().getStatusCode();
                System.out.println(status);
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            }
        };
        //  发起 API 调用
        String responseBody = null;
        try {
            responseBody = httpClient.execute(request, responseHandler);
        } catch (IOException ex) {
        }
        System.out.println(responseBody);
        return responseBody;
    }

    public static <T> T execute(HttpUriRequest request, ResponseHandler<T> responseHandler) {
        try {
            return httpClient.execute(request, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据返回自动JSON对象解析
     *
     * @param <T>
     * @param request
     * @param clazz
     * @return
     */
    public static <T> T executeJsonResult(HttpUriRequest request, Class<T> clazz) {
        return execute(request, JsonResponseHandler.createResponseHandler(clazz));
    }

    /**
     * 数据返回自动XML对象解析
     *
     * @param <T>
     * @param request
     * @param clazz
     * @return
     */
    public static <T> T executeXmlResult(HttpUriRequest request, Class<T> clazz) {
        T execute = execute(request, XmlResponseHandler.createResponseHandler(clazz));
        return execute(request, XmlResponseHandler.createResponseHandler(clazz));
    }

    /**
     * MCH keyStore 请求 数据返回自动XML对象解析
     *
     * @param <T>
     * @param mch_id
     * @param request
     * @param clazz
     * @return
     */
    public static <T> T keyStoreExecuteXmlResult(String mch_id, HttpUriRequest request, Class<T> clazz) {
        try {
            return httpClient_mchKeyStore.get(mch_id).execute(request, XmlResponseHandler.createResponseHandler(clazz));
        } catch (ClientProtocolException e) {
            log.debug("----错误信息--", e);
        } catch (IOException e) {
            log.debug("----错误信息--", e);
        }
        return null;
    }
}
