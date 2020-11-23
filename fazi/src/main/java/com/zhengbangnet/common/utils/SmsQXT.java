package com.zhengbangnet.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhengbangnet.common.lang.Setting;

public class SmsQXT {
	
	private static Logger logger = LoggerFactory.getLogger(SmsQXT.class);

	private final static Map<String, String> returnCodeMap = new HashMap<String, String>();
	
    static {
    	returnCodeMap.put("SUCCESS", "用户提交成功");
        returnCodeMap.put("USER_ERROR", "登录失败");
        returnCodeMap.put("USER_TYPE_ERROR", "非接口用户（可联系企信通客服修改用户为接口用户）");
        returnCodeMap.put("USER_IP_ERROR", "请求IP与注册IP不符");
        returnCodeMap.put("SEND_ERROR", "发送失败");
        returnCodeMap.put("TIMEOUT", "定时超过7天");
        returnCodeMap.put("Get method is forbidden!", "不支持GET方式提交");
    }
    
    /**
     * 异常代码识别
     * @param statusCode 异常代码
     * @return 错误信息
     */
    private static String getCause(String statusCode) {
        if (returnCodeMap.containsKey(statusCode)) {
            //根据错误码返回错误代码
            return statusCode + ":" + returnCodeMap.get(statusCode);
        }
        return statusCode + ":操作异常";
    }
	
	/**
	 * 通过URL地址的形式发送短信
	 * @param mobileNum
	 * @param content
	 * @return
	 */
	public static boolean sendMobileMessageByURL(String uid,String pwd,String sign,String mobile,String content) {
		
		Setting setting = SettingUtils.get();
		content=content+sign;
		logger.info("发送短信："+mobile+","+content);
		if(!setting.getIsSendMsg()){
			return true;
		}
		String state = "0";
		try {
	      URL url = new URL("http://115.29.103.223:8080/smsServer/submit");//短信URL地址
	      URLConnection connection = url.openConnection();
	      connection.setDoOutput(true);
	      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	      String MobileMessageInfo = "CORPID="+uid+"&CPPW="+MD5Util.encode(pwd)+"&PHONE="+mobile+"&CONTENT="+content;
	      out.write(MobileMessageInfo);
	      out.flush();
	      out.close();
	      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      state = br.readLine();
	      br.close();
	      if(state.equals("SUCCESS")){
	    	  logger.info("短信发送成功");
	    	  return true;
	      }
	      logger.warn("企信通发送短信："+getCause(state));
		  return false;
	    }catch (IOException e) {
	        logger.warn("企信通短信发送错误");
	        System.out.println(e);
	    }
	    return false;
	}
	
	/**
	 * 查询短信剩余数量
	 * 返回格式: 00&11&22&33
	 * 说明：00—剩余条数；11-退费条数；22-充值总条数；33-其他用户使用
	 * @return
	 */
	public static String querySurplus(String uid,String pwd){
		String state = "";
		try {
	      URL url = new URL("http://115.29.103.223:8080/smsServer/querySurplus?UID="+uid+"&PWD="+MD5Util.encode(pwd));//短信URL地址
	      URLConnection connection = url.openConnection();
	      connection.setDoOutput(true);
	      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	      out.flush();
	      out.close();
	      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      state = br.readLine();
	      br.close();
	      return state;
	    }catch (IOException e) {
	        logger.warn("企信通短信发送错误",e);
	    }
		return "";
	}
	
	
	public static void main(String args[]){
		
		sendMobileMessageByURL("jjy123456","jjy123456","【家佳源】","18562633410","您本次的验证码是572122,10分钟内有效");
/*		String surplus = querySurplus("","");
		String[] split = surplus.split("&");
		
		System.out.println(surplus);
		System.out.println(split[0]);*/
		
	}
	
	
	
	
	
}
