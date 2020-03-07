package com.javalsj.blog.autoconfigure.datasource;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

/**
 * @description 多数据源配置信息
 * @author WANGJIHONG
 * @date 2018年3月9日 下午12:16:18
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 1.配置文件包括1个主数据源和多个数据源，每个数据源单独一套配置，此处不支持多个数据源使用同一套连接池配置，目的是为了支持不同的数据库+不同的连接池配置来达到数据库性能优化的维护成本。
 *       <br/>
 *       2.多个数据源的和连接池配置项开头为:custom.spring.datasource.数据源标识.如扩展数据源标识为：first，则配置项为custom.spring.datasource.first.
 *       <br/>
 *       3.custom.spring.datasource.startDatabaseNames,
 *       start-db-names属性为启用的多数据源名称，若扩展配置中存在多个数据源配置，此选项可指定启用某些数据源，排名第一位的作为默认主数据源。
 */
@ConfigurationProperties(prefix = "custom.spring.datasource")
public class MultiDataSourceProperties implements InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(MultiDataSourceProperties.class);

	@Autowired
	private Environment environment;

	/**
	 * 本系统所启用的多数据源标识，多个用英文逗号分割,如：custom.spring.datasource.startDatabaseNames=default,first,second,thrid
	 * 标识本系统启用了default,first,second,thrid四个数据源，default排在第一位说明default标识的数据源为默认数据库。
	 */
	private List<String> startDatabaseNames;

	/**
	 * 系统所有多数据源容器 KEY:数据源NAME标识;VALUE:对应的数据源相关参数信息
	 */
	private final LinkedHashMap<String, MultiDataSourceProperty> startDatabaseProperties = new LinkedHashMap<>();

	/**
	 * 将创建的数据源信息缓存至系统中，后期动态切换数据源时可以从该缓存容器中获取数据源，
	 * KEY:数据源NAME标识;VALUE:对应的数据源DataSource对象
	 */
	private final LinkedHashMap<Object, Object> startDataSources = new LinkedHashMap<>();

	public void setStartDatabaseNames(List<String> startDatabaseNames) {
		this.startDatabaseNames = startDatabaseNames;
	}

	public Map<String, MultiDataSourceProperty> getStartDatabaseProperties() {
		return Collections.unmodifiableMap(startDatabaseProperties);
	}

	public Map<Object, Object> getStartDataSources() {
		return Collections.unmodifiableMap(startDataSources);
	}

	/**
	 * 根据启用的数据源获取数据源参数属性信息
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (startDatabaseNames != null && startDatabaseNames.size() > 0) {
			// 此处不使用并发遍历，保证启用的数据源标识顺序，从而识别出配置的第一个数据源为默认数据源
			startDatabaseNames.stream().filter(Objects::nonNull).forEach((startDatabaseName) -> {
				try {
					String startDataSourcePropValuePrefix = "custom.spring.datasource." + startDatabaseName;
					MultiDataSourceProperty properties = Binder.get(environment)
							.bind(startDataSourcePropValuePrefix, MultiDataSourceProperty.class).get();
					startDatabaseProperties.put(startDatabaseName, properties);
					// 根据数据源名称，数据源驱动连接池等配置类型创建数据源对象
					DataSource startDataSource = MultiDataSourceFactory.create(properties);
					startDataSources.put(startDatabaseName, startDataSource);
				} catch (Exception e) {
					logger.error("系统初始化数据源{}发生异常，请检查。", startDatabaseName, e);
				}
			});
		}
	}

}