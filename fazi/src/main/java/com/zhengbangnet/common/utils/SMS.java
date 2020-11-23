package com.zhengbangnet.common.utils;

public class SMS {

	public static final String CODE = "${CODE}";
	public static final String MINUTE = "${MINUTE}";
	
	/**
	 * 发送短信
	 * @param uid 用户id
	 * @param pwd 用户密码
	 * @param sign 签名
	 * @param mobile 手机号
	 * @param content 发送内容
	 * @return
	 */
	public static Boolean sendMsg(String uid,String pwd,String templateCode,String sign,String mobile, String content) {
//		return SmsQXT.sendMobileMessageByURL(uid,pwd,sign,mobile, content);
//		return SmsWebChinese.sendMsgWC(uid, pwd, mobile, content,"");
//		return SmsVcom.sendMsgVCOM(uid, pwd, mobile, content, sign, -1L);
//		return SmsAli.sendMsg(uid, pwd, templateCode, mobile, content, sign);
		return SmsDongFang.sendMsg(uid, pwd, mobile, content,sign);
	}
	
}
