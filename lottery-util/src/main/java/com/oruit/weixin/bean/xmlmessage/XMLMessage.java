package com.oruit.weixin.bean.xmlmessage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


public abstract class XMLMessage {

	private final String toUserName;
	private final String fromUserName;
	private final String msgType;

	protected XMLMessage(String toUserName, String fromUserName, String msgType) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.msgType = msgType;
	}

	/**
	 * 子类自定义XML
	 * @return
	 */
	public abstract String subXML();

	public String toXML(){
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>");
		sb.append("<CreateTime>").append(System.currentTimeMillis()/1000).append("</CreateTime>");
		sb.append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>");
		sb.append(subXML());
		sb.append("</xml>");
		return sb.toString();
	}

	public boolean outputStreamWrite(OutputStream outputStream){
		try {
			outputStream.write(toXML().getBytes("utf-8"));
			outputStream.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
