package com.javalsj.blog.autoconfigure.jwt;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

/**
 * @description JWT授权SERVLET初始
 * @author WANGJIHONG
 * @date 2018年5月1日 下午5:07:50 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Component
public class JwtServletContextInitializar implements ServletContextInitializer {

	/**
	 * Filter排除项
	 */
	private static final String JWT_FILTER_EXCLUSIONSVALUE = "/api/register,/api/login,/api/gc/amcard/*";

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		initJwtFilter(servletContext);
	}

	private void initJwtFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic jwtAuthenticationFilter = servletContext.addFilter("JwtAuthenticationFilter",
				new JwtAuthenticationFilter());
		jwtAuthenticationFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/api/*");
		jwtAuthenticationFilter.setInitParameter(JwtAuthenticationFilter.PARAM_NAME_EXCLUSIONS,
				JWT_FILTER_EXCLUSIONSVALUE);
	}

}
