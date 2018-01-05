package com.oruit.app.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.oruit.app.util.XMLConverUtil;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;

public class XmlResponseHandler{

    private static final Logger log = Logger.getLogger(XmlResponseHandler.class);
	private static Map<String, ResponseHandler<?>> map = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static <T> ResponseHandler<T> createResponseHandler(final Class<T> clazz){
		if(map.containsKey(clazz.getName())){
			return (ResponseHandler<T>)map.get(clazz.getName());
		}else{
			ResponseHandler<T> responseHandler = new ResponseHandler<T>() {
				@Override
				public T handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
	                if (status >= 200 && status < 300) {
	                    HttpEntity entity = response.getEntity();
	                    String str = EntityUtils.toString(entity,Charset.forName("utf-8"));
                            log.debug("-------请求微信回调的信息-----------------"+str);
	                   return XMLConverUtil.convertToObject(clazz,str);
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
