package com.zhengbangnet.common.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * https://www.cnblogs.com/binyue/p/4812798.html
 * https://www.jianshu.com/p/576dbf44b2ae
 * https://github.com/jwtk/jjwt
 */
public class JWT {
	
	public static final String JWT_NAME = "jwt_token";
	
	public static final String USER_ID = "USER_ID";
	public static final String USER_NAME = "USER_NAME";
	
	private static final String SECURITY = "fazi20181119dddddw151200";
	
	public static Claims parseJWT(String jsonWebToken) {
		try {
			byte[] encodeBase64 = Base64.encodeBase64((SECURITY).getBytes());
			String b64 = new String(encodeBase64);
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(b64)).parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 
	 * @param userId 用户id
	 * @param userName 用户姓名
	 * @param issuer 签发者
	 * @param TTLMillis 过期时间 毫秒
	 * @return
	 */
	public static String createJWT(String userId, String userName, String issuer, long TTLMillis) {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		// 生成签名密钥 
		byte[] encodeBase64 = Base64.encodeBase64(SECURITY.getBytes());
		String b64 = new String(encodeBase64);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(b64);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(USER_ID, userId);
		jsonObject.put(USER_NAME, userName);
		
		
		// 添加构成JWT的参数
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder()
				.setHeaderParam("typ", "JWT")//声明类型
				.setHeaderParam("alg", "HS256")//声明加密算法
				.setIssuer(issuer) // jwt签发者
				.setSubject(jsonObject.toString())//主题 放置内容吧
				//.setAudience(audience) // 接收jwt的一方
				.setIssuedAt(now) // 签发时间
				.signWith(signatureAlgorithm, signingKey); // 估计是第三段密钥
		
		// 添加Token过期时间
		if (TTLMillis >= 0) {
			// 过期时间
			long expMillis = nowMillis + TTLMillis;
			// 现在是什么时间
			Date exp = new Date(expMillis);
			// 系统时间之前的token都是不可以被承认的
			builder.setExpiration(exp).setNotBefore(now);
		}
		
		// 生成JWT
		return builder.compact();
	}
	
	public static void main(String[] args) {
		
		String jwt = createJWT("1", "李广岳", "http://xiaowooray.imwork.net", 10000);
		System.out.println(jwt);
		
		Claims parseJWT = parseJWT(jwt);
		String subject = parseJWT.getSubject();
		
		System.out.println(subject);
		JSONObject parseObject = JSONObject.parseObject(subject);
		System.out.println(parseObject.get("userId"));
		
	}

}
