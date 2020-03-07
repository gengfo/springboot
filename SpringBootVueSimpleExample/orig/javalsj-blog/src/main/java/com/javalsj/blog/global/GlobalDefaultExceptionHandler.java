package com.javalsj.blog.global;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** 
 * @description 全局异常处理
 * @author WANGJIHONG
 * @date 2018年2月27日 下午7:50:05 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	@ExceptionHandler(value = Exception.class) 
	public void defaultErrorHandler(HttpServletRequest request, Exception e) {
		// 打印异常信息：
		e.printStackTrace();
		System.out.println("GlobalDefaultExceptionHandler.defaultErrorHandler()");
	}
	
}
