package com.javalsj.blog.autoconfigure.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.PatternMatcher;
import com.alibaba.druid.util.ServletPathMatcher;

/**
 * @description JWT授权过滤器
 * @author WANGJIHONG
 * @date 2018年5月1日 下午5:06:29 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class JwtAuthenticationFilter implements Filter {
	/**
	 * 排除路径
	 */
	public static final String PARAM_NAME_EXCLUSIONS = "exclusions";
	private PatternMatcher pathMatcher = new ServletPathMatcher();

	private Set<String> excludesPattern;

	@Override
	public void init(FilterConfig config) throws ServletException {
		String exclusions = config.getInitParameter(PARAM_NAME_EXCLUSIONS);
		if (exclusions != null && exclusions.trim().length() != 0) {
			excludesPattern = new HashSet<String>(Arrays.asList(exclusions.split("\\s*,\\s*")));
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String requestURI = httpRequest.getRequestURI();
		String contextPath = httpRequest.getContextPath();
		if (isExclusion(contextPath, requestURI)) {
			chain.doFilter(request, response);
			return;
		}
		try {
			String token = httpRequest.getHeader("Authorization");
			// 检查jwt令牌, 如果令牌不合法或者过期, 里面会直接抛出异常, 下面的catch部分会直接返回
			JwtUtil.validateToken(token);
		} catch (Exception e) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}
		// 如果jwt令牌通过了检测, 那么就把request传递给后面的RESTful api
		chain.doFilter(request, response);
	}

	/**
	 * 是否为排除路径
	 */
	private boolean isExclusion(String contextPath, String requestURI) {
		if (excludesPattern == null || requestURI == null) {
			return false;
		}
		if (contextPath != null && requestURI.startsWith(contextPath)) {
			requestURI = requestURI.substring(contextPath.length());
			String prefix = "/";
			if (!requestURI.startsWith(prefix)) {
				requestURI = prefix + requestURI;
			}
		}
		for (String pattern : excludesPattern) {
			if (pathMatcher.matches(pattern, requestURI)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {

	}

}