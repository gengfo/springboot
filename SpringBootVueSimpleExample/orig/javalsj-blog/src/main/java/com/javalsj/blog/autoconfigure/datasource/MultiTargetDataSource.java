package com.javalsj.blog.autoconfigure.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @description 多数据源下支持在执行方法时指定使用哪个数据源
 * @author WANGJIHONG
 * @date 2018年3月1日 下午10:33:53
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 不使用声明式事务，全部使用编程式事务
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MultiTargetDataSource {
	/**
	 * 指定数据源标识
	 */
	String name() default "";
	
	/** 
	 * 是否启用事务，默认启用
	 */ 
	boolean startTransaction() default false;
}
