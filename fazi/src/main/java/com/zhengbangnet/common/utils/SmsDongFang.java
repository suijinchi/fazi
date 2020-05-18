package com.zhengbangnet.common.utils;

import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.utils.http.HttpClient;
import com.zhengbangnet.common.utils.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信工具类
 */
public class SmsDongFang {
	
	private static final String UID = "UID";
	private static final String KEY = "KEY";
	private static final String CONTENT = "CONTENT";
	private static final String MOBILE = "MOBILE";
	
	private static Logger logger = LoggerFactory.getLogger(SmsDongFang.class);
	
	private final static Map<Integer, String> returnCodeMap = new HashMap<Integer, String>();
	
    static {
        returnCodeMap.put(-1, "参数无效");
        returnCodeMap.put(-2, "通道不存在或者当前业务不支持此通道");
        returnCodeMap.put(-3, "定时格式错误");
        returnCodeMap.put(-4, "接收号码无效");
        returnCodeMap.put(-5, "提交号码个数超过上限,最多100条");
        returnCodeMap.put(-6, "短信内容长度不符合要求,普通短信70字");

		returnCodeMap.put(-7, "当前账户余额不足");
		returnCodeMap.put(-8, "网关发送短信时出现异常");
		returnCodeMap.put(-9, "用户或者密码没输入");
		returnCodeMap.put(-10, "企业ID或者会员账号不存在");
		returnCodeMap.put(-11, "密码错误");
		returnCodeMap.put(-12, "账户锁定");
		returnCodeMap.put(-13, "网关状态关闭");
		returnCodeMap.put(-14, "验证用户时执行异常");
		returnCodeMap.put(-15, "网关初始化失败");
		returnCodeMap.put(-16, "当前IP已被系统屏蔽,密码失败次数太多");
		returnCodeMap.put(-17, "发送异常");
		returnCodeMap.put(-18, "账号未审核");

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
	public static boolean sendMsg(String uid,String pwd,String mobile,String content, String sign){
		Setting setting = SettingUtils.get();
		logger.info("发送短信："+mobile+","+content+sign);
		if(!setting.getIsSendMsg()){
			return true;
		}
		try {
			String url = "http://api.xhsms.com/gb2312/web_api/?x_eid=0&x_uid="+uid+"&x_pwd_md5="+MD5Util.encode(pwd)+"&x_ac=10&x_gate_id=300&x_target_no="+mobile+"&x_memo="+URLEncoder.encode(content+sign, "gb2312");
			System.out.println(url);
			URLEncoder.encode("","utf-8");
//			String url = URL.replace(UID, URLEncoder.encode(uid, "UTF-8")).replace(KEY, pwd).replace(MOBILE, mobile).replace(CONTENT, URLEncoder.encode(content+sign, "UTF-8"));
			HttpClient client = new HttpClient();
			Response response = client.get(url);
			String status = response.asString();
			int statusInt = Integer.parseInt(status);
			if(statusInt>0){
				logger.info("短信发送成功");
				return true;
			}
			logger.warn("发送短信错误:"+getCause(statusInt));
			return false;
		} catch (Exception e) {
			logger.warn("发送短信错误");
			e.printStackTrace();
		}
		return false;
	}
	
 /*   public static boolean sendMsgWC(String mobile,String content){
		logger.info("短信发送成功："+mobile+","+content);
		return true;
    }*/
	
	public static void main(String[] args) throws UnsupportedEncodingException {

//		System.out.println(URLEncoder.encode("您本次的验证码是：123456", "gb2312"));
//		sendMsg("fazi0532","fazi0532","18562633410", "您本次的验证码是：123456","【法滋蛋糕】");
		sendMsg("fazi0532","fazi0532","18562633410", "您再[法滋蛋糕]的支付订单退款已成功受理,请您到该订单状态页查看退款详情","【法滋蛋糕】");

	}
}
