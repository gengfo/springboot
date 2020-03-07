package com.javalsj.blog.result;

import org.springframework.http.HttpStatus;

/**
 * @description 响应码枚举，参考 HTTP状态码的语义
 * @author WANGJIHONG
 * @date 2018年3月13日 下午8:35:00
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public enum ResultCode {
	/** 
	 * 成功
	 */ 
	SUCCESS(HttpStatus.OK),
	
	/** 
	 * 失败 
	 */ 
	FAIL(HttpStatus.BAD_REQUEST),
	
	/** 
	 * 未认证（签名错误）
	 */ 
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
	
	/** 
	 * 接口不存在
	 */ 
	NOT_FOUND(HttpStatus.NOT_FOUND),
	
	/** 
	 * 服务器内部错误
	 */ 
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

	public int code;

	ResultCode(HttpStatus httpStatus) {
		this.code = httpStatus.value();
	}
	
}
