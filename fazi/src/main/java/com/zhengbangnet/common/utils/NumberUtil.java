package com.zhengbangnet.common.utils;

import java.math.BigDecimal;

public class NumberUtil {
	
	public static String getFormatNumber(double num,int scale){
		return new BigDecimal(num).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	public static String getFormatNumber(Double iMeasure) {
		return getFormatNumber(iMeasure,2);
	}
	
}
