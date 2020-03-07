package com.javalsj.blog.autoconfigure.datasource;

import java.util.Map;

import javax.sql.DataSource;

/** 
 * @description 数据源属性信息默认绑定器
 * @author WANGJIHONG
 * @date 2018年3月5日 下午10:42:17 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 备注信息
 */
public class MultiDataSourceDefaultBinder implements MultiDataSourceBinder {

	@Override
	public void bind(final DataSource dataSource, Map<Object, Object> dataSourceProperties) {
	}

}
