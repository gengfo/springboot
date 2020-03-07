package com.javalsj.blog.autoconfigure.datasource;

import java.util.Map;

import javax.sql.DataSource;

/** 
 * @description 数据源属性绑定器接口
 * @author WANGJIHONG
 * @date 2018年3月5日 下午10:42:43 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
public interface MultiDataSourceBinder {
	
	/** 
	 * 绑定数据源相关属性信息（连接池属性等） 
	 * @param @param dataSource 被绑定的数据源对象
	 * @param @param dataSourceProperties 被绑定的数据源对象的配置属性
	 * @throws Exception
	 */ 
	void bind(final DataSource dataSource, final Map<Object, Object> dataSourceProperties) throws Exception;
	
}
