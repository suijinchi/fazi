package org.weixin4j.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;  

/**
 * java实现AES256加密解密 
 * 依赖说明： 
 * bcprov-jdk15-133.jar：PKCS7Padding 
 * javabase64-1.3.1.jar：base64 
 * local_policy.jar 和 US_export_policy.jar需添加到%JAVE_HOME%\jre\lib\security中（lib中版本适合jdk1.7） 
 *
 */
public class AES256Utils {

	public static boolean initialized = false;  
    
    public static final String ALGORITHM = "AES/ECB/PKCS7Padding";
      
    /** 
     * @param  String str  要被加密的字符串 
     * @param  byte[] key  加/解密要用的长度为32的字节数组（256位）密钥 
     * @return byte[]  加密后的字节数组 
     */  
    public static byte[] Aes256Encode(String str, byte[] key){
        initialize();  
        byte[] result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            result = cipher.doFinal(str.getBytes("UTF-8"));  
        }catch(Exception e){
            e.printStackTrace();  
        }  
        return result;  
    }  
      
    /** 
     * @param  byte[] bytes  要被解密的字节数组 
     * @param  byte[] key    加/解密要用的长度为32的字节数组（256位）密钥 
     * @return String  解密后的字符串 
     */  
    public static String Aes256Decode(byte[] bytes, byte[] key){
        initialize();  
        String result = null;
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = cipher.doFinal(bytes);  
            result = new String(decoded, "UTF-8");
        }catch(Exception e){
            e.printStackTrace();  
        }  
        return result;  
    }  
      
    public static void initialize(){  
        if (initialized) return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }
    
}
