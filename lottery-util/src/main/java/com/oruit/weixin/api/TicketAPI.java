package com.oruit.weixin.api;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import com.oruit.weixin.bean.Ticket;
import com.oruit.app.client.LocalHttpClient;
import com.oruit.app.common.cache.Cache;
import com.oruit.app.common.cache.CacheManager;
//import static com.oruit.weixin.api.BaseAPI.Default_AppId;
import java.util.Date;

/**
 * JSAPI ticket
 * 
 *
 */
public class TicketAPI extends BaseAPI{

	/**
	 * 获取 jsapi_ticket
	 * @param access_token
	 * @return
	 */
	public static Ticket ticketGetticket(String access_token){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(BASE_URI + "/cgi-bin/ticket/getticket")
				.addParameter("access_token",access_token)
				.addParameter("type", "jsapi")
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,Ticket.class);
	}
        
        /**
	 * 获取access_token从缓存（90分钟有效）
         * 使用默认的密钥（appid：wx78dd2e283de7d769；secret：f4483973b09ca92cee4b4b1887939089）
         * @param access_token
	 * @return
	 */
        public static String getTicketFromCache(String access_token){
//            String chekey = "accTt";
//            Cache c = CacheManager.getCacheInfo(chekey);
//            
//            if(c==null)
//            {
//                Ticket tt = ticketGetticket(access_token);
//                long l = 1000*60*90;
//                c = new Cache();
//                c.setExpired(true);
//                c.setKey(chekey);
//                c.setValue(String.valueOf(tt.getTicket()));
//                c.setTimeOut(l+System.currentTimeMillis());
//                CacheManager.clearOnly(chekey);
//                CacheManager.putCacheInfo(chekey, c, l);
//            }
//            return (String) c.getValue();

            String chekey = access_token;
            Cache c = CacheManager.getCacheInfo(chekey);
            
            if(c==null)
            {
                Ticket tt = ticketGetticket(access_token);
                long l = 1000*60*90;
                c = new Cache();
                c.setExpired(true);
                c.setKey(chekey);
                c.setValue(String.valueOf(tt.getTicket()));
                c.setTimeOut(l+System.currentTimeMillis());
                CacheManager.clearOnly(chekey);
                CacheManager.putCacheInfo(chekey, c, l);
            }
            return (String) c.getValue();
        }
}
