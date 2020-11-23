/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.weixin4j.org/sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhengbangnet.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * 将微盟POST的流转换为XStream，然后转换为InputMessage对象
 *
 */
public class JStreamFactory {

    /**
     * 将输入流转读取成字符串
     *
     * @param in 输入流
     * @return 字符串
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String inputStream2String(InputStream in) throws UnsupportedEncodingException, IOException {
/*    	StringBuffer sb = new StringBuffer() ; 
    	InputStreamReader isr = new InputStreamReader(in);
    	BufferedReader br = new BufferedReader(isr); 
    	String s = "" ; 
    	while((s=br.readLine())!=null){ 
    		sb.append(s) ; 
    	} 
    	String str =sb.toString(); 
    	return str;*/
		if (in == null) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n, "UTF-8"));
        }
        return out.toString();

    }
}
