package com.javalsj.blog.autoconfigure.datasource;

import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;

/** 
 * @description 创建多数据源工厂类
 * @author WANGJIHONG
 * @date 2018年3月5日 下午8:42:35 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
public class MultiDataSourceFactory {

	/**
	 * 根据数据源配置属性信息来创建对应类型的数据源
	 * @param multiDataSourceProperties 多数据源连接配置信息属性
	 * @return DataSource 数据源对象
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public static DataSource create(final MultiDataSourceProperty multiDataSourceProperty) throws Exception {
		// 数据源类型
		String type = multiDataSourceProperty.getType();
		Class<? extends DataSource> dataSourceClass = (Class<? extends DataSource>) Class.forName(type);
		// 读取数据源连接配置，构建数据源对象
		String driverClassName = multiDataSourceProperty.getDriverClassName();
		String url = multiDataSourceProperty.getUrl();
		String username = multiDataSourceProperty.getUsername();
		String password = multiDataSourceProperty.getPassword();
		// 将数据源对象设为final，后续绑定数据防止该对象被修改
		final DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
				.username(username).password(password).type(dataSourceClass).build();
		
		// 绑定数据源其他参数信息
		MultiDataSourceBinder multiDataSourceBinder;
		if (DruidDataSource.class.getName().equals(type)) {
			multiDataSourceBinder = new MultiDataSourceDruidBinder();
		} else if (HikariDataSource.class.getName().equals(type)) {
			multiDataSourceBinder = new MultiDataSourceHikariBinder();
		} else {
			multiDataSourceBinder = new MultiDataSourceDefaultBinder();
		}
		// 转化成不可变数据源配置集合传递给绑定器，不可变是为了控制绑定器不可增加、删除、修改、只允许取元素值，防止其他地方处理修改了元素。
		Map<Object, Object> dataSourceProperties = Collections.unmodifiableMap(multiDataSourceProperty);
		multiDataSourceBinder.bind(dataSource, dataSourceProperties);	
		return dataSource;
	}
}
