package com.zhengbangnet.common.utils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.utils.http.HttpClient;
import com.zhengbangnet.common.utils.http.Response;

/**
 * 短信工具类
 */
public class SmsWebChinese {
	
	private static final String UID = "UID";
	private static final String KEY = "KEY";
	private static final String CONTENT = "CONTENT";
	private static final String MOBILE = "MOBILE";
	
	private static Logger logger = LoggerFactory.getLogger(SMS.class);
	
	/**
	 * 网建平台地址
	 */
	private static final String URL_WEBCHINESE = "http://utf8.sms.webchinese.cn/?Uid="+UID+"&Key="+KEY+"&smsMob="+MOBILE+"&smsText="+CONTENT;
	
	private final static Map<Integer, String> returnCodeMap = new HashMap<Integer, String>();
	
    static {
        returnCodeMap.put(-1, "没有该用户账户");
        returnCodeMap.put(-2, "接口密钥不正确");
        returnCodeMap.put(-3, "短信数量不足");
        returnCodeMap.put(-4, "没有该用户账户");
        returnCodeMap.put(-6, "没有该用户账户");
        returnCodeMap.put(-11, "该用户被禁用");
        returnCodeMap.put(-14, "短信内容出现非法字符");
        returnCodeMap.put(-21, "MD5接口密钥加密不正确");
        returnCodeMap.put(-41, "手机号码为空");
        returnCodeMap.put(-42, "短信内容为空");
        returnCodeMap.put(-51, "短信签名格式不正确");
    }

    /**
     * 异常代码识别
     * @param statusCode 异常代码
     * @return 错误信息
     */
    private static String getCause(int statusCode) {
        if (returnCodeMap.containsKey(statusCode)) {
            //根据错误码返回错误代码
            return statusCode + ":" + returnCodeMap.get(statusCode);
        }
        return statusCode + ":操作异常";
    }
	
	/**
	 * 网建平台发送验证码
	 * @param mobile 手机号
	 * @param content 内容
	 * @param sign 
	 * @return 状态码
	 */
	public static boolean sendMsgWC(String uid,String pwd,String mobile,String content, String sign){
		Setting setting = SettingUtils.get();
		logger.info("发送短信："+mobile+","+content+sign);
		if(!setting.getIsSendMsg()){
			return true;
		}
		try {
			String url = URL_WEBCHINESE.replace(UID, URLEncoder.encode(uid, "UTF-8")).replace(KEY, pwd).replace(MOBILE, mobile).replace(CONTENT, URLEncoder.encode(content+sign, "UTF-8"));
			HttpClient client = new HttpClient();
			Response response = client.get(url);
			String status = response.asString();
			int statusInt = Integer.parseInt(status);
			if(statusInt>0){
				logger.info("短信发送成功");
				return true;
			}
			logger.warn("网建发送短信错误:"+getCause(statusInt));
			return false;
		} catch (Exception e) {
			logger.warn("网建发送短信错误");
			e.printStackTrace();
		}
		return false;
	}
	
 /*   public static boolean sendMsgWC(String mobile,String content){
		logger.info("短信发送成功："+mobile+","+content);
		return true;
    }*/
	
	public static void main(String[] args) {
		
//		sendMsgWC(SettingUtils.get().getMsgUid(),SettingUtils.get().getMsgKey(),"18562633410", "您本次的验证码是：123456","【青岛交运集团】");
		sendMsgWC("gaoguoquan","1b5033610639932b6377","18562633410", "您本次的验证码是：123456","");
//		SMS.sendMsgWC("18562633410", "您于2017-01-10的申请提现已经提交成功，提现积分280,提现金额：2.8");
		
	}
}
