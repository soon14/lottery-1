package com.oruit.weixin.api;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import com.oruit.weixin.bean.Token;
import com.oruit.app.client.LocalHttpClient;
import com.oruit.app.common.cache.Cache;
import com.oruit.app.common.cache.CacheManager;
import java.util.Date;
import org.apache.log4j.Logger;

public class TokenAPI extends BaseAPI{

    private static final Logger log = Logger.getLogger(TokenAPI.class);
    
	/**
	 * 获取access_token
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static Token token(String appid,String secret,String code){
//		HttpUriRequest httpUriRequest = RequestBuilder.post()
//				.setUri(BASE_URI + "/cgi-bin/token")
//				.addParameter("grant_type","client_credential")
//				.addParameter("appid", appid)
//				.addParameter("secret", secret)
//				.build();
//		return LocalHttpClient.executeJsonResult(httpUriRequest,Token.class);
                HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(BASE_URI + "/cgi-bin/token")
				.addParameter("grant_type","authorization_code")
				.addParameter("appid", appid)
				.addParameter("secret", secret)
						.addParameter("code",code)
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,Token.class);
	}
        
        /**
	 * 获取access_token(从服务器获取新的Token，慎用)
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static String getToken(String appid,String secret,String code){
		return token(appid,secret,code).getAccess_token();
	} 
        
        /**
        * 获取access_token从缓存（90分钟有效）
        * 使用默认的密钥（appid：wx78dd2e283de7d769；secret：f4483973b09ca92cee4b4b1887939089）
        * @return
        * */
        public static String getTokenFromCache(String appid, String secret,String code){
            String chekey = "accTk" + appid;
            Cache c = CacheManager.getCacheInfo(chekey);

            if(c==null)
            {
                Token tk = token(appid,secret,code);
                long l = 1000*60*90;
                c = new Cache();
                c.setExpired(true);
                c.setKey(chekey);
                c.setValue(String.valueOf(tk.getAccess_token()));
                c.setTimeOut(l+System.currentTimeMillis());
                CacheManager.clearOnly(chekey);
                CacheManager.putCacheInfo(chekey, c, l);
            }
            return (String) c.getValue();
        }
}
