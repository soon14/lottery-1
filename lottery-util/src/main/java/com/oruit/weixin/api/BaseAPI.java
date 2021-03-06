package com.oruit.weixin.api;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

public abstract class BaseAPI {
//        protected static final String Default_AppId = "wx4af5be76f31c4511";
//        protected static final String Default_Secret = "423460a75af8fef30984cd8170057adc";
//        protected static final String Default_AppId = "wx78dd2e283de7d769";
//        protected static final String Default_Secret = "f4483973b09ca92cee4b4b1887939089";
	protected static final String BASE_URI = "https://api.weixin.qq.com";
	protected static final String MEDIA_URI = "http://file.api.weixin.qq.com";
	protected static final String QRCODE_DOWNLOAD_URI = "https://mp.weixin.qq.com";
	protected static final String MCH_URI = "https://api.mch.weixin.qq.com";
	protected static final String OPEN_URI = "https://open.weixin.qq.com";

	protected static Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE,ContentType.APPLICATION_JSON.toString());
	protected static Header xmlHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE,ContentType.APPLICATION_XML.toString());
}
