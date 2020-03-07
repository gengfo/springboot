package com.javalsj.blog.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description 统一 API响应结果封装
 * @author WANGJIHONG
 * @date 2018年3月13日 下午8:44:29
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 控制Result权限，构建结果Result对象统一使用com.javalsj.blog.vo.ResultFactory工厂类来创建
 */
@Data
@AllArgsConstructor
public class Result {
	/** 
	 * 响应令牌
	 */ 
	private String token;
	/**
	 * 响应状态码
	 */
	private int code;
	/**
	 * 响应提示信息
	 */
	private String message;
	/**
	 * 响应结果对象
	 */
	private Object data;

}
