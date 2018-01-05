/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.weixin.api;

import com.oruit.app.client.LocalHttpClient;
import com.oruit.app.util.XMLConverUtil;
import static com.oruit.weixin.api.BaseAPI.xmlHeader;
import com.oruit.weixin.bean.TransferReqData;
import com.oruit.weixin.bean.TransfersResData;
import java.nio.charset.Charset;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

/**
 *
 * @author hanfeng
 */
public class TransfersAPI extends BaseAPI {
    
    private static final Logger log = Logger.getLogger(TransfersAPI.class);
    /**
     * 发红包
     * @param appId 
     * @param secret
     * @param mchId 微信支付的商品号
     * @param keystorPath 微信证书
     * @param transfersReqData 企业支付入参数对下
     * @return 
     */
    public static TransfersResData TransfersPay(String mchId,String keystorPath,TransferReqData transfersReqData){
        String unifiedorderXML = XMLConverUtil.convertToXML(transfersReqData);
        log.debug("-----------------------------------------------"+unifiedorderXML);
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                                .setHeader(xmlHeader)
				.setUri(MCH_URI + "/mmpaymkttransfers/promotion/transfers")
				.setEntity(new StringEntity(unifiedorderXML,Charset.forName("utf-8")))
				.build();
        LocalHttpClient.initMchKeyStore(mchId, keystorPath);
        return LocalHttpClient.keyStoreExecuteXmlResult(mchId, httpUriRequest, TransfersResData.class);
    }
}
