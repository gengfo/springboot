package com.javalsj.blog.autoconfigure.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/** 
 * @description 多数据源动态切换路由类
 * @author WANGJIHONG
 * @date 2018年2月28日 下午7:49:46
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo Spring动态配置多数据源，即在大型应用中对数据进行切分，并且采用多个数据库实例进行管理，这样可以有效提高系统的水平伸缩性。
 * <br/> 而这样的方案就会不同于常见的单一数据实例的方案，这就要程序在运行时根据当时的请求及系统状态来动态的决定将数据存储在哪个数据库实例中，以及从哪个数据库提取数据。
 * <br/> 动态数据源能进行自动切换的核心就是spring底层提供了AbstractRoutingDataSource类进行数据源的路由的，该类充当了DataSource的路由中介， 
 * <br/> 能有在运行时，根据某种key值来动态切换到真正的DataSource上。我们主要继承这个类，实现里面的方法即可实现我们想要的，
 * <br/> 这里主要是实现方法：determineCurrentLookupKey()，而此方法只需要返回一个数据库的名称即可，
 * <br/> 所以我们核心的是有一个类来管理数据源的线程池，这个类才是动态数据源的核心处理类，另外就是使用AOP技术在执行事务方法前进行数据源的切换。
 */
public class MultiDataSourceRouting extends AbstractRoutingDataSource {
	
	private final Logger logger = LoggerFactory.getLogger(MultiDataSourceRouting.class);
	
	@Override
	protected Object determineCurrentLookupKey() {
		// MultiDataSourceContextHolder代码中使用setDataSourceName设置当前的数据源，在路由类中使用getDataSourceName进行获取，交给AbstractRoutingDataSource进行注入使用。
		String dataSourceName = MultiDataSourceContextHolder.getDataSourceName();
		logger.info("系统当前上下文使用的数据源是 [{}]", dataSourceName);
		return dataSourceName;
	}

}
