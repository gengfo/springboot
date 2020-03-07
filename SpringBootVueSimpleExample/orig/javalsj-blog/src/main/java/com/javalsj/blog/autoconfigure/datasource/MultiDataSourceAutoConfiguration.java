package com.javalsj.blog.autoconfigure.datasource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description 多数据源自动配置类
 * @author WANGJIHONG
 * @date 2018年3月9日 下午11:12:55 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo @EnableConfigurationProperties 自动映射一个POJO到Spring Boot配置文件（默认是application.properties文件）的属性集，
 * <br/> @ConditionalOnClass 当类路径存在这个类时才会加载这个配置类，否则跳过,这个很有用比如不同jar包间类依赖，依赖的类不存在直接跳过，不会报错
 */
@Configuration
@EnableConfigurationProperties(MultiDataSourceProperties.class)
@ConditionalOnClass({MultiDataSourceRouting.class, MultiDataSourceService.class})
@ConditionalOnProperty(prefix = "custom.spring.datasource", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MultiDataSourceAutoConfiguration {
	
	@Autowired
	private MultiDataSourceProperties multiDataSourceProperties;
	
	/**
	 * 注册多数据源动态切换路由Bean
	 * @ConditionalOnMissingBean 这个配置就是SpringBoot可以优先使用自定义Bean的核心所在，如果没有我们的自定义Bean那么才会自动配置一个新的Bean
	 * @return DynamicDataSourceRouting 多数据源动态切换路由对象
	 */
	@Bean
	@ConditionalOnMissingBean(MultiDataSourceRouting.class)
	public MultiDataSourceRouting multiDataSourceRouting() {
		Map<Object, Object> startDataSources = multiDataSourceProperties.getStartDataSources();
		// 创建多数据源动态路由对象并添加多数据源场景下的第一个数据源为默认数据源
		MultiDataSourceRouting multiDataSourceRouting = new MultiDataSourceRouting();
		multiDataSourceRouting.setDefaultTargetDataSource(startDataSources.values().iterator().next());
		multiDataSourceRouting.setTargetDataSources(startDataSources);
		multiDataSourceRouting.afterPropertiesSet();
		return multiDataSourceRouting;
	}
	
	@Bean
	@ConditionalOnMissingBean(MultiDataSourceService.class)
	public MultiDataSourceService multiDataSourceService() {
		Map<Object, Object> startDataSources = multiDataSourceProperties.getStartDataSources();
		return new MultiDataSourceService(startDataSources);
	}
	
}