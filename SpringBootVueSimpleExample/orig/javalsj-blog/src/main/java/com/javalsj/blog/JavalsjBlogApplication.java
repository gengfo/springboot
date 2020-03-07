package com.javalsj.blog;

import java.nio.charset.Charset;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description 博客系统入口启动类
 * @author WANGJIHONG
 * @date 2018年2月11日 下午7:17:24
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 由于本系统采用自定义的多数据源配置来加载数据源，所以需要将springboot自带的DataSourceAutoConfiguration禁用，
 * 若不禁用它会读取application.properties文件的spring.datasource.*属性并自动配置单数据源。
 */
@EnableTransactionManagement
@PropertySource("classpath:config/datasource.properties")
@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class JavalsjBlogApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(JavalsjBlogApplication.class);
		application.setBannerMode(Banner.Mode.CONSOLE);
		application.run(args);
	}

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return converter;
	}

    /**
     * 跨域过滤器
     */
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(source);
//    }
    
}