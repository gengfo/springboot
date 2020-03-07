package com.javalsj.blog.autoconfigure.datasource;

import java.util.Properties;

/**
 * @description 单个数据源对象连接配置信息属性
 * @author WANGJIHONG
 * @date 2018年3月5日 下午7:21:44
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 默认提供数据源的基本信息属性获取的方法，其他属性全放在MultiDataSourceProperty属性中
 */
public class MultiDataSourceProperty extends Properties {

	private static final long serialVersionUID = 1L;

	/**
	 * 获取数据源连接池类型
	 * 
	 * @return String 数据源连接池类型，如：com.alibaba.druid.pool.DruidDataSource
	 */
	public String getType() {
		return getProperty("type");
	}

	/**
	 * 获取数据源驱动类名
	 * 
	 * @return String 数据源驱动名，如：com.mysql.cj.jdbc.Driver
	 */
	public String getDriverClassName() {
		return getProperty("driverClassName");
	}

	/**
	 * 获取数据源访问URL信息
	 * 
	 * @return String
	 *         数据源URL，如：jdbc:mysql://localhost:3306/javalsjblog_first?characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC
	 */
	public String getUrl() {
		return getProperty("url");
	}

	/**
	 * 获取数据源访问用户名
	 * 
	 * @return String 数据源用户名，如:root
	 */
	public String getUsername() {
		return getProperty("username");
	}

	/**
	 * 获取数据源访问密码
	 * 
	 * @return String 数据库密码，如:root
	 */
	public String getPassword() {
		return getProperty("password");
	}

}
