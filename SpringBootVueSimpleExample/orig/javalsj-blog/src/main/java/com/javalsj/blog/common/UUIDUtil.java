package com.javalsj.blog.common;

import java.util.UUID;

/**
 * @description UUID生成工具类 
 * @author WANGJIHONG
 * @date 2018年2月28日 下午8:35:30 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
public class UUIDUtil {
	
	 public static String newUUID(){
         return UUID.randomUUID().toString().replace("-", "");
    }

}
