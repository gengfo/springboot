package com.javalsj.blog.autoconfigure.druid;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @description Druid数据源连接池监控平台拦截配置
 * @author WANGJIHONG
 * @date 2018年3月2日 下午11:16:03
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Component
public class DruidServletContextInitializer implements ServletContextInitializer {
	/**
	 * 默认Druid连接池Servlet路径
	 */
	private static final String DEFAULT_URL_PATTERNS_DRUID_STAT_VIEW_SERVLET = "/druid/*";

	/**
	 * 默认Druid连接池Filter路径
	 */
	private static final String DEFAULT_URL_PATTERNS_DRUID_WEB_STAT_FILTER = "/*";

	/**
	 * 默认Druid连接池Filter排除项
	 */
	private static final String DEFAULT_URL_PATTERNS_DRUID_WEB_STAT_FILTER_EXCLUSIONSVALUE = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";

	/**
	 * 启用参数键标识
	 */
	private static final String PARAM_NAME_ENABLE = "enable";

	/**
	 * URL匹配地址参数键标识
	 */
	private static final String PARAM_NAME_URL_PATTERN = "urlPattern";

	/**
	 * Druid 注册的Servlet名称
	 */
	private static final String DRUID_STAT_VIEW_SERVLET_NAME = "druidStatViewServlet";

	/**
	 * Druid 注册的Servlet属性前缀标识
	 */
	private static final String DRUID_STAT_VIEW_SERVLET_PROPERTIES_KEY_PREFIX = "custom.spring.datasource.druid.stat-view-servlet";

	/**
	 * Druid 注册的Filter过滤器名称
	 */
	private static final String DRUID_DRUID_WEB_STAT_FILTER_NAME = "druidWebStatFilter";

	/**
	 * Druid 注册的Filter属性前缀标识
	 */
	private static final String DRUID_WEB_STAT_FILTER_PROPERTIES_KEY_PREFIX = "custom.spring.datasource.druid.web-stat-filter";

	@Autowired
	private Environment environment;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		initDruidStatViewServlet(servletContext);
		initDruidWebStatFilter(servletContext);
	}

	/**
	 * Druid数据源状态监控
	 * 
	 * @param servletContext
	 *            上下文
	 */
	private void initDruidStatViewServlet(ServletContext servletContext) {
		Properties druidProperties = Binder.get(environment)
				.bind(DRUID_STAT_VIEW_SERVLET_PROPERTIES_KEY_PREFIX, Properties.class).get();
		boolean statViewServletEnabled = false;
		if (druidProperties.containsKey(PARAM_NAME_ENABLE)) {
			String statViewServletEnabledValue = druidProperties.getProperty(PARAM_NAME_ENABLE);
			statViewServletEnabled = Boolean.parseBoolean(statViewServletEnabledValue);
		}
		if (!statViewServletEnabled) {
			return;
		}
		ServletRegistration.Dynamic druidStatViewServlet = servletContext.addServlet(DRUID_STAT_VIEW_SERVLET_NAME,
				new StatViewServlet());
		if (druidProperties.containsKey(PARAM_NAME_URL_PATTERN)) {
			String urlPatternValue = druidProperties.getProperty(PARAM_NAME_URL_PATTERN);
			druidStatViewServlet.addMapping(urlPatternValue);
		} else {
			druidStatViewServlet.addMapping(DEFAULT_URL_PATTERNS_DRUID_STAT_VIEW_SERVLET);
		}
		// 添加属性
		Arrays.asList(StatViewServlet.PARAM_NAME_ALLOW, StatViewServlet.PARAM_NAME_DENY,
				StatViewServlet.PARAM_NAME_USERNAME, StatViewServlet.PARAM_NAME_PASSWORD,
				StatViewServlet.PARAM_NAME_RESET_ENABLE, StatViewServlet.PARAM_REMOTE_ADDR,
				StatViewServlet.PARAM_NAME_JMX_PASSWORD, StatViewServlet.PARAM_NAME_JMX_URL,
				StatViewServlet.PARAM_NAME_JMX_USERNAME, StatViewServlet.SESSION_USER_KEY)
				.forEach((key) -> {
					String value = druidProperties.getProperty(key);
					if (!Objects.isNull(value)) {
						druidStatViewServlet.setInitParameter(key, value);
					}
				});
	}

	/**
	 * Druid过滤器
	 * 
	 * @param servletContext
	 *            上下文
	 */
	public void initDruidWebStatFilter(ServletContext servletContext) {
		Properties druidProperties = Binder.get(environment)
				.bind(DRUID_WEB_STAT_FILTER_PROPERTIES_KEY_PREFIX, Properties.class).get();
		boolean webStatFilterEnabled = false;
		FilterRegistration.Dynamic druidWebStatFilter = servletContext.addFilter(DRUID_DRUID_WEB_STAT_FILTER_NAME,
				new WebStatFilter());
		druidWebStatFilter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true,
				DRUID_STAT_VIEW_SERVLET_NAME);
		if (druidProperties.containsKey(PARAM_NAME_ENABLE)) {
			String webStatFilterEnabledValue = druidProperties.getProperty(PARAM_NAME_ENABLE);
			webStatFilterEnabled = Boolean.parseBoolean(webStatFilterEnabledValue);
		}
		if (!webStatFilterEnabled) {
			return;
		}
		if (druidProperties.containsKey(PARAM_NAME_URL_PATTERN)) {
			String urlPatternValue = druidProperties.get(PARAM_NAME_URL_PATTERN).toString();
			druidWebStatFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, urlPatternValue);
		} else {
			druidWebStatFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true,
					DEFAULT_URL_PATTERNS_DRUID_WEB_STAT_FILTER);
		}
		if (druidProperties.containsKey(WebStatFilter.PARAM_NAME_EXCLUSIONS)) {
			String exclusionsValue = druidProperties.getProperty(WebStatFilter.PARAM_NAME_EXCLUSIONS);
			druidWebStatFilter.setInitParameter(WebStatFilter.PARAM_NAME_EXCLUSIONS, exclusionsValue);
		} else {
			druidWebStatFilter.setInitParameter(WebStatFilter.PARAM_NAME_EXCLUSIONS,
					DEFAULT_URL_PATTERNS_DRUID_WEB_STAT_FILTER_EXCLUSIONSVALUE);
		}
		Arrays.asList(WebStatFilter.PARAM_NAME_PRINCIPAL_COOKIE_NAME, WebStatFilter.PARAM_NAME_PRINCIPAL_SESSION_NAME,
				WebStatFilter.PARAM_NAME_PROFILE_ENABLE, WebStatFilter.PARAM_NAME_REAL_IP_HEADER,
				WebStatFilter.PARAM_NAME_SESSION_STAT_ENABLE, WebStatFilter.PARAM_NAME_SESSION_STAT_ENABLE,
				WebStatFilter.PARAM_NAME_SESSION_STAT_MAX_COUNT)
				.forEach((key) -> {
					String value = druidProperties.getProperty(key);
					if (!Objects.isNull(value)) {
						druidWebStatFilter.setInitParameter(key, value);
					}
				});
	}

}
