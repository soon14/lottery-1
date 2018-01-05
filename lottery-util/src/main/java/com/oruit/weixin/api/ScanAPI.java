/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.weixin.api;

import com.oruit.app.client.LocalHttpClient;
import com.oruit.app.util.JsonDealUtil;
import com.oruit.app.util.XMLConverUtil;
import com.oruit.weixin.bean.ScanPayReqData;
import com.oruit.weixin.bean.ScanPayResData;
import java.nio.charset.Charset;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

/**
 * 发红包
 * @author Liuk
 */
public class ScanAPI extends BaseAPI{
     private static final Logger log = Logger.getLogger(ScanAPI.class);
    /**
     * 发红包
     * @param appId 
     * @param secret
     * @param mchId 微信支付的商品号
     * @param keystorPath 微信证书
     * @param scanPayReqData 红包入参数对下
     * @return 
     */
    public static ScanPayResData sendredpack(String appId,String secret,String mchId,String keystorPath
                                                ,ScanPayReqData scanPayReqData){
        String unifiedorderXML = XMLConverUtil.convertToXML(scanPayReqData);
                log.debug("-----红包支付----"+scanPayReqData.toString());
        log.debug("-----------------------------------------------"+unifiedorderXML);
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                                .setHeader(xmlHeader)
				.setUri(MCH_URI + "/mmpaymkttransfers/sendredpack")
				.setEntity(new StringEntity(unifiedorderXML,Charset.forName("utf-8")))
				.build();
        LocalHttpClient.initMchKeyStore(mchId, keystorPath);
        return LocalHttpClient.keyStoreExecuteXmlResult(mchId, httpUriRequest, ScanPayResData.class);
    }
} 
