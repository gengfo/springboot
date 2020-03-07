package com.javalsj.blog.result;

/**
 * @description 响应结果生成工厂类
 * @author WANGJIHONG
 * @date 2018年3月13日 下午8:36:58
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class ResultFactory {

	public static Result buildSuccessResult(Object data) {
		return buildSuccessResult(null, data);
	}
	
	public static Result buildSuccessResult(String token, Object data) {
		return buidResult(token, ResultCode.SUCCESS, "成功", data);
	}

	public static Result buildFailResult(String message) {
		return buildFailResult(null, message);
	}
	
	public static Result buildFailResult(String token, String message) {
		return buidResult(token, ResultCode.FAIL, message, null);
	}

	public static Result buidResult(String token, ResultCode resultCode, String message, Object data) {
		return buidResult(token, resultCode.code, message, data);
	}
	
	public static Result buidResult(String token, int resultCode, String message, Object data) {
		return new Result(token, resultCode, message, data);
	}
	
}