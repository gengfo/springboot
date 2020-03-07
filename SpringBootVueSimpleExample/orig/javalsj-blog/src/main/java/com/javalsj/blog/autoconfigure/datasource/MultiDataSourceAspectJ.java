package com.javalsj.blog.autoconfigure.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description 多数据源切面切换数据源
 * @author WANGJIHONG
 * @date 2018年3月1日 下午7:32:34
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo @Order(-1)保证该AOP在@Transactional之前执行
 */
@Aspect
@Order(-1)	
@Component
public class MultiDataSourceAspectJ {

	/**
	 * 切换数据库拦截器
	 */
	@Before("@annotation(multiTargetDataSource)")
	public void changeDataSource(JoinPoint point, MultiTargetDataSource multiTargetDataSource) {
		if (multiTargetDataSource == null) {
			// 方法无MultiTargetDataSource注解指定数据源，则方法执行结束后不执行销毁
			return;
		}
		MultiDataSourceContextHolder.setDataSourceName(multiTargetDataSource.name());
	}

	/**
	 * 销毁数据源 在所有的方法执行执行完毕后
	 */
	@After("@annotation(multiTargetDataSource)")
	public void destroyDataSource(JoinPoint point, MultiTargetDataSource multiTargetDataSource) {
		if (multiTargetDataSource == null) {
			// 方法无MultiTargetDataSource注解指定数据源，则方法执行结束后不执行销毁
			return;
		}
		MultiDataSourceContextHolder.destroyDataSource(multiTargetDataSource.name());
	}
	
}
