package com.javalsj.blog.autoconfigure.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @description JWT认证工具
 * @author WANGJIHONG
 * @date 2018年5月6日 下午7:09:51
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 备注信息
 */
public class JwtUtil {

	/**
	 * 加密解密的KEY
	 */
	private static final String SECRET = "JavalsjSecret";

	/**
	 * 根据用户名生成TOKEN
	 * 
	 * @param username
	 *            用户名
	 */
	public static String generateToken(String username) {
		HashMap<String, Object> map = new HashMap<>(1);
		map.put("username", username);
		String jwt = Jwts.builder().setClaims(map)
				.setExpiration(new Date(System.currentTimeMillis() + 24 * 1000 * 1000L))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		return "Bearer " + jwt;
	}

	/**
	 * 校验TOKEN有效性
	 * 
	 * @param token
	 *            令牌
	 */
	public static Map<String, Object> validateToken(String token) {
		Map<String, Object> body;
		try {
			body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace("Bearer ", "")).getBody();
		} catch (Exception e) {
			throw new IllegalStateException("Invalid Token. " + e.getMessage());
		}
		return body;
	}

}
