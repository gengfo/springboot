package com.javalsj.blog.autoconfigure.datasource;

import java.util.Collections;
import java.util.Map;

/**
 * @description TODO(这里用一句话描述这个类的作用) 
 * @author WANGJIHONG
 * @date 2018年3月12日 下午9:01:05 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class MultiDataSourceService {
	
	/**
	 * 系统所有多数据源容器 KEY:数据源NAME标识;VALUE:对应的数据源DataSource对象
	 */
	private final Map<Object, Object> dataSources;

	public MultiDataSourceService(Map<Object, Object> dataSources) {
		this.dataSources = dataSources;
	}
	
	/** 
	 * 获取所有启用的多数据源信息
	 * @return LinkedHashMap<Object,Object> 返回所有启用的多数据源信息，KEY:数据源name标识；VALUE:对应的数据源DataSource对象
	 */ 
	public Map<Object, Object> getDataSources() {
		return Collections.unmodifiableMap(dataSources);
	}
	
}
