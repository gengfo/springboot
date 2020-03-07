package com.javalsj.blog.autoconfigure.datasource;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @description Hikari连接池属性信息绑定器 
 * @author WANGJIHONG
 * @date 2018年3月14日 下午10:27:45 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class MultiDataSourceHikariBinder implements MultiDataSourceBinder {

	@Override
	public void bind(DataSource dataSource, Map<Object, Object> dataSourceProperties) throws Exception {
		Properties dsProperties = new Properties();
		dataSourceProperties.forEach((key, value)-> dsProperties.put(key, value));
		((HikariDataSource)dataSource).setDataSourceProperties(dsProperties);
	}

}
