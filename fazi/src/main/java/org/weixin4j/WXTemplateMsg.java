package org.weixin4j;

import java.util.HashMap;

public class WXTemplateMsg {
	
	private String touser; //接收者openid  
	  
    private String template_id; //模板ID  
  
    private String url; //模板跳转链接  
  
    private HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>(); //data数据  
  
    public String getTouser() {  
        return touser;  
    }  
  
    public void setTouser(String touser) {  
        this.touser = touser;  
    }  
  
    public String getTemplate_id() {  
        return template_id;  
    }  
  
    public void setTemplate_id(String template_id) {  
        this.template_id = template_id;  
    }  
  
    public String getUrl() {  
        return url;  
    }  
  
    public void setUrl(String url) {  
        this.url = url;  
    }
    
	public HashMap<String, HashMap<String, String>> getData() {
		return data;
	}

	public void setData(HashMap<String, HashMap<String, String>> data) {
		this.data = data;
	}

	/**
	 * 添加模板键值
	 * @param key 键
	 * @param value 值
	 * @param color 颜色
	 */
	public void addItem(String key,String value,String color){
		HashMap<String, String> data = new HashMap<String,String>();
		data.put("value", value);
		data.put("color", color);
		this.data.put(key, data);
	}
	
	/**
	 * 添加模板键值
	 * @param key 键
	 * @param value 值
	 */
	public void addItem(String key,String value){
		HashMap<String, String> data = new HashMap<String,String>();
		data.put("value", value);
		this.data.put(key, data);
	}
}
