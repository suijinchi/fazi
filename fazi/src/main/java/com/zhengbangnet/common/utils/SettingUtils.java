/*
 * 
 * 
 * 
 */
package com.zhengbangnet.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.zhengbangnet.common.lang.Ignore;
import com.zhengbangnet.common.lang.Setting;

/**
 * Utils - 系统设置
 * 
 * 
 * 
 */
public final class SettingUtils {

	private static Setting setting = new Setting();
	
    private static Properties props = new Properties();
	
    private static final String CONFIG_FILE_NAME = "constants.properties";
    
    
	static{
		 try {   
        	InputStream in= SettingUtils.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            props.load(isr);
            in.close();
            
            Field[] field = setting.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
            try {
                for (int j = 0; j < field.length; j++) { // 遍历所有属性
                    String name = field[j].getName(); // 获取属性的名字
                    String value = props.getProperty(name);
                    Ignore annotation = field[j].getAnnotation(Ignore.class);
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                    if(value==null||annotation!=null){
                    	continue;
                    }
                    String type = field[j].getGenericType().toString(); // 获取属性的类型
                    if (annotation==null&&type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                        Method m = setting.getClass().getMethod("set"+name,String.class);
                        m.invoke(setting, value);
                    }
                    if (annotation==null&&type.equals("class java.lang.Integer")) {
                        Method m = setting.getClass().getMethod("set" + name,Integer.class);
                        Integer integer=Integer.parseInt(value);
                        m.invoke(setting, integer);
                    }
                    if (annotation==null&&type.equals("class java.lang.Long")) {
                        Method m = setting.getClass().getMethod("set" + name,Long.class);
                        Long integer=Long.parseLong(value);
                        m.invoke(setting, integer);
                    }
                    if (annotation==null&&type.equals("class java.lang.Boolean")) {
                        Method m = setting.getClass().getMethod("set" + name,Boolean.class);
                        m.invoke(setting, Boolean.valueOf(value));
                    }
                    if (annotation==null&&type.equals("class java.math.BigDecimal")) {
                        Method m = setting.getClass().getMethod("set" + name,BigDecimal.class);
                        BigDecimal b = new BigDecimal(value);
                        m.invoke(setting, b);
                    }
                    // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            
        } catch (FileNotFoundException e) {   
            e.printStackTrace();   
        } catch (IOException e) {          
        	e.printStackTrace();   
        }   
	}
	
	/**
	 * 不可实例化
	 */
	private SettingUtils() {}
	
	/**
	 * 获取系统设置
	 * 
	 * @return 系统设置
	 */
	public static Setting get() {
		return setting;
	}

	/**
	 * 设置系统设置
	 * 
	 * @param set
	 *            系统设置
	 */
	public static void set(Setting set) {
		
		Field[] field = set.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                Ignore annotation = field[j].getAnnotation(Ignore.class);
                String type = field[j].getGenericType().toString(); // 获取属性的类型
                
                if(annotation!=null){
                	continue;
                }
            	if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = set.getClass().getMethod("get" + name);
                    Method getM = setting.getClass().getMethod("set"+name,String.class);
                    String value = (String) m.invoke(set); // 调用getter方法获取属性值
                    if(StringUtils.isBlank(value)){
                    	continue;
                    }
                    getM.invoke(setting, value);
                    props.setProperty(field[j].getName(), value.toString());
                }
                if (type.equals("class java.lang.Integer")) {
                    Method m = set.getClass().getMethod("get" + name);
                    Method getM = setting.getClass().getMethod("set"+name,Integer.class);
                    Integer value = (Integer) m.invoke(set);
                    if(value==null){
                    	continue;
                    }
                    getM.invoke(setting, value);
                    props.setProperty(field[j].getName(), value.toString());
                }
                if (type.equals("class java.lang.Long")) {
                    Method m = set.getClass().getMethod("get" + name);
                    Method getM = setting.getClass().getMethod("set"+name,Long.class);
                    Long value = (Long) m.invoke(set);
                    if(value==null){
                        continue;
                    }
                    getM.invoke(setting, value);
                    props.setProperty(field[j].getName(), value.toString());
                }
                if (type.equals("class java.lang.Boolean")) {
                    Method m = set.getClass().getMethod("get" + name);
                    Method getM = setting.getClass().getMethod("set"+name,Boolean.class);
                    Boolean value = (Boolean) m.invoke(set);
                    if(value==null){
                    	continue;
                    }
                    getM.invoke(setting, value);
                    props.setProperty(field[j].getName(), value.toString());
                }
                if (type.equals("class java.math.BigDecimal")) {
                    Method m = set.getClass().getMethod("get" + name);
                    Method getM = setting.getClass().getMethod("set"+name,BigDecimal.class);
                    BigDecimal value = (BigDecimal) m.invoke(set);
                    if(value==null){
                    	continue;
                    }
                    getM.invoke(setting, value);
                    props.setProperty(field[j].getName(), value.toString());
                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        OutputStream fos;
		try {
			fos = new FileOutputStream(new File(SettingUtils.class.getClassLoader().getResource("constants.properties").toURI()));
			props.store(fos, "Update value");
		}catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
}