package com.zhengbangnet.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SnUtils {
	
    /**
     * 产生支付宝支付订单号
     */
	public static String getAlipaySn(long userId){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return "A"+format.format(new Date())+userId%10;
	}
	
	/**
	 * 微信单号 
	 */
	public static String getWechatSn(long userId){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return "W"+format.format(new Date())+userId%10;
	}
	
	/**
	 * 订单
	 * @param userId
	 * @return
	 */
	public static String getOrdersSn(long userId){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date())+userId%10;
	}
	
	/**
	 * 生成充值单号
	 */
	public static String getChargeOrderSn(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 1; i++) {
            int number = random.nextInt(10);
            sb.append(number);
        }
		return format.format(new Date())+sb.toString();
	}
	
}
