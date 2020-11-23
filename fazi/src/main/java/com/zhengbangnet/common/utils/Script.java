package com.zhengbangnet.common.utils;

import java.math.BigDecimal;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Script {

	public static void main(String args[]) {
		try {
			BigDecimal price = new BigDecimal(10);
			int service = 3;
			String str = "price*0.8 - service";
			System.out.println(str);
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			engine.put("price", price);
			engine.put("service", service);
			Object result = engine.eval(str);
			System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
