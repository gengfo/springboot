package com.javalsj.blog.autoconfigure.datasource;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

/** 
 * @description Druid连接池属性信息绑定器
 * @author WANGJIHONG
 * @date 2018年3月5日 下午10:44:49 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
public class MultiDataSourceDruidBinder implements MultiDataSourceBinder {

	@Override
	public void bind(DataSource dataSource, Map<Object, Object> dataSourceProperties) throws SQLException {
		Properties properties = new Properties();
		// 将元素KEY前面添加druid.前缀，然后利用DruidDataSource的configFromPropety方法填充数据源信息。
		dataSourceProperties.forEach((key, value) -> properties.put("druid." + key, value));
		((DruidDataSource)dataSource).configFromPropety(properties);
		// 需要调用init方法，若不调用则druid数据源查看页面会报 (*) property for user to setup错误信息。
		((DruidDataSource)dataSource).init();
	}

}
