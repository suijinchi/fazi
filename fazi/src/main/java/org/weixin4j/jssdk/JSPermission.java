package org.weixin4j.jssdk;

import java.util.ArrayList;
import java.util.Arrays;

import org.weixin4j.util.SHA1;

public class JSPermission {
	
	//页面需要使用的参数
	private String appId;
	private String timestamp;
	private String noncestr;
	private String signature;
	
	private String jsapi_ticket;
	private String url;
	
	/**
	 * 
	 * @param timestamp 时间戳
	 * @param noncestr 随即字符串
	 * @param jsapi_ticket 
	 * @param url 当前网页地址
	 */
	public JSPermission(String appId,String timestamp,String noncestr,String jsapi_ticket,String url){
		this.appId = appId;
		this.timestamp = timestamp;
		this.noncestr = noncestr;
		this.jsapi_ticket = jsapi_ticket;
		this.url = url;
		this.signature = this.getSign();
	}
	
	public JSPermission(){
		
	}
	
	public String getSign(){
		ArrayList<String> list = new ArrayList<String>();
		//list.add(entry.getKey() + "=" + entry.getValue() + "&");
		list.add("noncestr="+noncestr+"&");
		list.add("jsapi_ticket="+jsapi_ticket+"&");
		list.add("timestamp="+timestamp+"&");
		list.add("url="+url);
		
		int size = list.size();
		String [] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < size; i ++) {
		    sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result = SHA1.encode(result);
		return result;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getJsapi_ticket() {
		return jsapi_ticket;
	}

	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
